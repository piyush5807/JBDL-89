package com.example.digital_library.controllers;

import com.example.digital_library.dtos.CreateBookRequest;
import com.example.digital_library.dtos.GetBooksRequest;
import com.example.digital_library.models.Book;
import com.example.digital_library.services.BookService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        this.bookService.add(createBookRequest);
    }

    @GetMapping("/books")
    public List<Book> getBooks(@Valid @RequestBody GetBooksRequest getBooksRequest){
        return bookService.findBooks(getBooksRequest);
    }
}
