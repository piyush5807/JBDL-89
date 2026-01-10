package com.example.demo_jpa;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.util.ArrayBuilders;

@RestController
public class BookController {

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book) {
        System.out.println("Inside createBook, book - " + book);

//        Book book2 = new Book();
//        System.out.println(book2);

//        Book book1 = new Book();
//        book1.setBookAuthorName("Jim");
//        book1.setAvailable(true);
//
//
        Book book2 = Book.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .genre(book.getGenre())
                .build();
//
//        Book book3 = new Book(book.getId(), book.getBookName(),
//                null, book.getGenre(), false,
//                null, null, null);


        return book;
    }

}
