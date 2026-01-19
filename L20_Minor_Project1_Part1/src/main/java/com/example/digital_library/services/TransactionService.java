package com.example.digital_library.services;

import com.example.digital_library.dtos.InitiateTransactionRequest;
import com.example.digital_library.models.Book;
import com.example.digital_library.models.Student;
import com.example.digital_library.models.Transaction;
import com.example.digital_library.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    public String initiateTransaction(InitiateTransactionRequest initiateTransactionRequest) {
        Transaction transaction = initiateTransactionRequest.to();
        if(transaction.getTransactionType().equals(TransactionType.ISSUE)) {
            return issueTxn(transaction);
        }

        return returnTxn(transaction);

    }

    private String issueTxn(Transaction transaction){
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



        // get the numbers the books currently issued to a particular student
        // select * from book where student_id = ?

        return null;

    }

    private String returnTxn(Transaction transaction){
        return null;
    }
}
