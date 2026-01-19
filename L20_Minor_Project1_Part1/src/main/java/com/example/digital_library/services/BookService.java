package com.example.digital_library.services;

import com.example.digital_library.dtos.CreateBookRequest;
import com.example.digital_library.models.Author;
import com.example.digital_library.models.Book;
import com.example.digital_library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    public void add(CreateBookRequest createBookRequest){

        Book book = createBookRequest.to();
        // Step1: Creating the author if not present, else retrieving the existing author
        this.authorService.addOrGetAuthor(book.getAuthor()); // author is present in the author table
//        book.setAuthor(authorFromDB);

        // Fetch the admin record from the admin table given the admin email; // Only if client passes admin email instead of admin id

        // Step2: Adding the book along with the author and admin
        this.bookRepository.save(book);
    }

    public Book findById(Integer bookId){
        return this.bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> findBooksByStudentId(Integer studentId){
        return this.bookRepository.findBooksIssuedToStudent(studentId);
    }
}
