package com.example.demo_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.util.ArrayBuilders;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * POST : Add the data in the database + in the cache
     *        book:b1   - Book object
     *        String    -   conversion of book object --> something which is understandable by redis
     * GET : 1) Fetch the data from cache
     *       2) If present, then return that data itself, otherwise fetch the data from mysql db
     *       3) Add the data fetched from db to cache
     */

    @GetMapping("/book/{bookId}")
    public Book getBook(@PathVariable("bookId") Integer bookId) {
        return this.bookService.getBook(bookId);
    }

    @PostMapping("/book")
    public void createBook(@RequestBody BookCreateRequest bookCreateRequest) {
        this.bookService.createBookV2(bookCreateRequest);

//        Book book2 = new Book();
//        System.out.println(book2);

//        Book book1 = new Book();
//        book1.setBookAuthorName("Jim");
//        book1.setAvailable(true);
//
//
//        Book book2 = Book.builder()
//                .id(book.getId())
//                .bookName(book.getBookName())
//                .genre(book.getGenre())
//                .build();
//
//        Book book3 = new Book(book.getId(), book.getBookName(),
//                null, book.getGenre(), false,
//                null, null, null);

    }

}
