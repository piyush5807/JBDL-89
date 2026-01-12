package com.example.demo_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

//    public void createBook(Book book) {
//        this.bookRepository.save(book);
//    }

    private String generateBookCode(String bookName) {
        return bookName.replaceAll(" ", "_");
    }

    public void createBookV2(BookCreateRequest bookCreateRequest) {
        Book book = bookCreateRequest.to();
        book.setBookCode(this.generateBookCode(bookCreateRequest.getBookName()));
        this.bookRepository.save(book);
    }
}
