package com.example.digital_library;

import com.example.digital_library.models.Book;
import com.example.digital_library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DigitalLibraryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
//        List<Book> books = this.bookRepository.getBooksWrittenByAuthor(2);
//        System.out.println(books);
    }

}
