package com.example.digital_library.exceptions;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(String message) {
        super(message);
    }
}
