package com.example.digital_library.services;

import com.example.digital_library.dtos.InitiateTransactionRequest;
import com.example.digital_library.exceptions.BookNotFoundException;
import com.example.digital_library.models.*;
import com.example.digital_library.repositories.TransactionRepository;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    BookService bookService;

    @Autowired
    TxnServiceHelper txnServiceHelper;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StudentService studentService;

    @Value("${library.book-issuance.max-threshold}")
    @Setter
    private Integer maxThreshold;

    public String initiateTransaction(InitiateTransactionRequest initiateTransactionRequest) throws Exception {
        Transaction transaction = initiateTransactionRequest.to();
        if(transaction.getTransactionType().equals(TransactionType.ISSUE)) {
            return issueTxn(transaction);
        }

        return returnTxn(transaction);

    }

    public String issueTxn(Transaction transaction) throws Exception {
        /**
         * Step 1: Validate the request
         * 1. Book id and student id is valid
         * 2. Book is available or not for issuance
         * 3. Student should not have the number of books issued >= threshold defined at an application level
         *
         *
         * Step 2: Create a transaction in the pending state
         *
         * Step 3: Make the book unavailable i.e assign the student id in the book table for this particular book
         *
         * Step 4: Complete the transaction
         */


        Book book = this.bookService.findById(transaction.getBook().getId());
        Student student = this.studentService.findById(transaction.getStudent().getId());

        // Step 1

        if(book == null){
            logger.error("Book not found");
            throw new BookNotFoundException("Book not found");
        }

        if(student == null || book.getStudent() != null){
            logger.warn("Book or Student is invalid");
            throw new IllegalArgumentException("Book or Student is invalid or unavailable for issuance");
        }

        List<Book> studentBookList = this.bookService.findBooksByStudentId(transaction.getStudent().getId());

        if(!studentBookList.isEmpty() && studentBookList.size() >= maxThreshold){
            logger.warn("Student has already issued max threshold books");
            throw new BadRequestException("Student has already issued max threshold books");
        }

        Date dueDate = txnServiceHelper.calculateDueDate();
        transaction.setDueDate(dueDate);

        // Step 2
        Transaction savedTxn = this.transactionRepository.save(transaction);

//        book = this.bookService.findById(savedTxn.getBook().getId()); // this is not required
        try {

            // Step 3
            this.bookService.assignBookToStudent(transaction.getBook().getId(), transaction.getStudent().getId());

            // step 4
//        this.transactionRepository.updateStatus(savedTxn.getId(), TransactionStatus.SUCCESS);
            savedTxn.setTransactionStatus(TransactionStatus.SUCCESS);
            this.transactionRepository.save(savedTxn);
        }catch (Exception e){
            savedTxn.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(savedTxn);
        }

        // get the numbers the books currently issued to a particular student
        // select * from book where student_id = ?

        return savedTxn.getExternalTxnId();

    }

    private String returnTxn(Transaction transaction) throws BadRequestException {

        /**
         * Step 1: validate student id , book id, book must be assigned to that student only who is requesting
         * Step 2: Calculate fine // there's a need to find the issue txn first
         * Step 3: Create a txn in pending state
         * Step 4: Unassigning the book from the student
         * Step 5: Complete the txn
         */

        Book book = this.bookService.findById(transaction.getBook().getId());
        Student student = this.studentService.findById(transaction.getStudent().getId());
        if(book == null || student == null || book.getStudent() == null || !book.getStudent().getId().equals(student.getId())){
            logger.warn("Book is not assigned to this student");
            throw new BadRequestException("Book is not assigned to this student");
        }

        Transaction issueTxn = this.transactionRepository.findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student, book, TransactionType.ISSUE);
        Long fine = this.txnServiceHelper.calculateFine(issueTxn.getDueDate());
        transaction.setDueDate(issueTxn.getDueDate());

        transaction.setFine(fine);
        Transaction savedTxn = this.transactionRepository.save(transaction);

        try {

            book.setStudent(null); // unassigning the book in the object and then saving it in the db in next row
            this.bookService.add(book);
            savedTxn.setTransactionStatus(TransactionStatus.SUCCESS);

        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            this.transactionRepository.save(savedTxn);
        }

        return savedTxn.getExternalTxnId();
    }


}
