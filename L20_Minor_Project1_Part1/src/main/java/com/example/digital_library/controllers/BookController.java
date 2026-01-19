package com.example.digital_library.controllers;

import com.example.digital_library.dtos.CreateBookRequest;
import com.example.digital_library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        this.bookService.add(createBookRequest);
    }
}
