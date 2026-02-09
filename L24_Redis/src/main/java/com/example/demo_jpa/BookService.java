package com.example.demo_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCacheRepository bookCacheRepository;

    private static Logger logger = Logger.getLogger(BookService.class.getName());

    private String generateBookCode(String bookName) {
        return bookName.replaceAll(" ", "_");
    }

    public Book getBook(Integer bookId) {
        logger.info("Getting book with id: " + bookId + ", in thread - " + Thread.currentThread().getName());
        /**
        GET : 1) Fetch the data from cache
              2) If present, then return that data itself, otherwise fetch the data from mysql db
              3) Add the data fetched from db to cache
        */

        Book book = this.bookCacheRepository.getBookAsHash(bookId);
        if(book == null) {
            book = this.bookRepository.findById(bookId).orElse(null);

            // add this book in the cache
            if(book != null) {
                final Book bookToBeSaved = book;
                Runnable runnable = () -> {
                    logger.info("Inside thread - " + Thread.currentThread().getName());
                    this.bookCacheRepository.addBookAsHash(bookToBeSaved);
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
        }




        logger.info("Returning book with id: " + bookId + ", in thread - " + Thread.currentThread().getName());

        return book;

    }

    public void createBookV2(BookCreateRequest bookCreateRequest) {
        Book book = bookCreateRequest.to();
        book.setBookCode(this.generateBookCode(bookCreateRequest.getBookName()));
        this.bookRepository.save(book);

        // Adding in cache
        this.bookCacheRepository.addBookAsHash(book);
    }
}
