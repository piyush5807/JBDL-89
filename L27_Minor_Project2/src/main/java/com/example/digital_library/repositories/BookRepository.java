package com.example.digital_library.repositories;

import com.example.digital_library.models.Book;
import com.example.digital_library.models.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * JPQL vs Native Query
     *
     * JPQL - Java persistence query language
     *        This is written keeping java objects in mind
     * Native query - This is written keeping sql tables and columns in mind
     *
     * In native query there's a disadvantage of not getting the exception in case of wrong queries on the application start itself
     * and instead we get an exception when the corresponding function is invoked.
     *
     * Native query can be easier for developers to write
     *
     *
     *
     */

    List<Book> findByGenre(Genre genre);

    @Query(value = "select * from book where b.title like '%?1%' ", nativeQuery = true)
    List<Book> findByTitle(String title);

    List<Book> findByAuthorEmail(String author);

    @Query(value = "select * from Book where student_id = ?1", nativeQuery = true) // written in jpql
//    @Query("select b from Book b where b.student = :sId")
//    @Query(value = "select * from Book b where b.student_id = ?1", nativeQuery = true)
    List<Book> findBooksIssuedToStudent(Integer sId);

    /**
     * Parse this query to understand what needs to be done
     * Convert this query into a native one to pass it to the underlying db because db does not understand jpql format
     */

    /**
     * Hibernate does not do any modification because in native query the onus is on the developer to write keeping sql
     * table nomenclature in mind
     */

//    @Query(value = "select * from book b where b.author = ?1", nativeQuery = true)
    List<Book> getBooksWrittenByAuthor(Integer authorId);

    @Modifying
    @Transactional
    @Query(value = "update book b set b.student_id = ?2 where b.id = ?1", nativeQuery = true)
    void assignBookToStudent(Integer bookId, Integer studentId);

    /**
     * unsafe - is inconsistency possible if multiple requests try to execute at the same time ?
     * INSERT - execute
     * DELETE - execute
     * UPDATE - execute
     *
     * safe
     * SELECT - executeQuery
     */

}
