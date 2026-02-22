package com.example.digital_library.repositories;

import com.example.digital_library.models.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Transactional
    @Query("update Transaction t set t.transactionStatus = ?2 where t.id = ?1")
    void updateStatus(Integer id, TransactionStatus status);

//    @Query(value = "select * from transaction where student_id = ?1 and book_id = ?2 and transaction_type = ?3 order by id desc limit 1 ", nativeQuery = true)
//    Transaction getIssueTransactionForReturn(Integer studentId, Integer bookId, TransactionType transactionType);

    Transaction findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);
}

// statement.executeQuery expected results...  // coming from the underlying database
// expecting a select query but found update Query // coming from hibernate