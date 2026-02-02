package com.example.digital_library.services;

import com.example.digital_library.exceptions.BookNotFoundException;
import com.example.digital_library.models.*;
import com.example.digital_library.repositories.BookRepository;
import com.example.digital_library.repositories.TransactionRepository;
import org.apache.coyote.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    /**
     * Junit - used to write unit test cases
     * Mockito - used to mock certain responses and classes
     *
     * @InjectMocks - Annotation used to set up an actual object
     * Whenever you annotate an object say o1 as @InjectMocks, and annotate the dependencies of that o1
     * with @Mock, what mockito does is, it attaches the dependency as an actual object under the parent object o1
     *
     * Whereas if you annotate an object say o1 as @Mock, and annotate the dependencies of that o1 with
     * @Mock, mockito does not attach the dependency with the parent object.
     *
     * @Mock - Annotation used to set a dummy object.
     */

    @InjectMocks // real object
    TransactionService transactionService;

    @Mock // dummy
    BookService bookService;

    @Mock
    StudentService studentService;

    @Mock
    TxnServiceHelper txnServiceHelper;

    @Mock
    TransactionRepository transactionRepository;

    private Transaction transaction;
    private Book book;
    private Student student;

    private String externalTxnId = UUID.randomUUID().toString();

//    @Autowired - Will not work in test classes if you are using Junit and Mockito
//    TransactionService transactionService2;

    public void init(){
        this.transactionService.setMaxThreshold(3);
        this.txnServiceHelper.setDuration(15);
        this.transaction = Transaction.builder()
//                .externalTxnId(this.externalTxnId)
                .student(
                        Student.builder().id(1).build()
                ).book(
                        Book.builder().id(1).build()
                ).transactionType(
                        TransactionType.ISSUE
                )
                .build();

        this.book = Book.builder()
                .id(1)
                .title("Intro to Git")
                .genre(Genre.PROGRAMMING)
                .author(
                        Author.builder()
                                .id(1)
                                .name("James Bond")
                                .email("james@uk.co")
                                .build()
                )
                .build();

        this.student = Student.builder()
                .id(1)
                .name("Aditya")
                .age(19)
                .rollNumber("2K26/CO/001")
                .email("aditya@gmail.com")
                .build();
    }

    @Test(expected = BookNotFoundException.class)
    public void testIssueTxn_BookNotFound() throws Exception {

        init();

        when(bookService.findById(eq(1))).thenReturn(null);
        transactionService.issueTxn(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIssueTxn_StudentNotFound() throws Exception {
        init();

        when(bookService.findById(eq(1))).thenReturn(this.book);
        when(studentService.findById(eq(1))).thenReturn(null);
        transactionService.issueTxn(transaction);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIssueTxn_BookAlreadyAssigned() throws Exception {

        init();
        this.book.setStudent(this.student); // Book is assigned to a student

        when(bookService.findById(eq(1))).thenReturn(book);
        when(studentService.findById(eq(1))).thenReturn(student);

        transactionService.issueTxn(transaction);

    }

    @Test
    public void testIssueTxn() throws Exception {

        init();

        List<Book> bookList = Arrays.asList(
                Book.builder().id(2).build(),
                Book.builder().id(3).build()
        );

        Transaction savedTxn = Transaction.builder()
                .id(1)
                .transactionType(TransactionType.ISSUE)
                .book(this.book)
                .student(this.student)
                .externalTxnId(externalTxnId)
                .build();


        when(bookService.findById(eq(1))).thenReturn(this.book);
        when(studentService.findById(eq(1))).thenReturn(this.student);
        when(bookService.findBooksByStudentId(eq(1))).thenReturn(bookList);

        when(transactionRepository.save(any())).thenReturn(savedTxn);
        doNothing().when(bookService).assignBookToStudent(eq(1), eq(1));

//        when(transactionRepository.save(any())).thenReturn(savedTxn);

//        doCallRealMethod().when(bookService).assignBookToStudent(eq(1), eq(1)); for void function, calling the real method instead of mocking the data
//        when(bookService.findById(eq(1))).thenCallReadMethod(); for non-void functions, calling the real method instead of mocking the data

        String txnId = this.transactionService.issueTxn(transaction);

        Assert.assertEquals(txnId, savedTxn.getExternalTxnId());

        verify(bookService, times(1)).findById(eq(1));
        verify(studentService, times(1)).findById(eq(1));
        verify(bookService, times(1)).findBooksByStudentId(eq(1));
        verify(transactionRepository, times(2)).save(any());
    }

}
