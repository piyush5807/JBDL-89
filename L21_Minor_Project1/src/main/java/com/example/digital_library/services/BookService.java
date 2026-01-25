package com.example.digital_library.services;

import com.example.digital_library.dtos.CreateBookRequest;
import com.example.digital_library.dtos.GetBooksRequest;
import com.example.digital_library.models.Author;
import com.example.digital_library.models.Book;
import com.example.digital_library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void add(Book book){
        this.bookRepository.save(book);
    }

    public Book findById(Integer bookId){
        return this.bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> findBooksByStudentId(Integer studentId){
        return this.bookRepository.findBooksIssuedToStudent(studentId);
    }

    public void assignBookToStudent(Integer bookId, Integer studentId){
        this.bookRepository.assignBookToStudent(bookId, studentId);
    }

    public List<Book> findBooks(GetBooksRequest getBooksRequest){

//        String sql = "select * from book b";

        if(getBooksRequest.getAuthorEmail() != null){
//            sql += " where b.author.email like '%?1%'";
            return this.bookRepository.findByAuthorEmail(getBooksRequest.getAuthorEmail());
        }else if(getBooksRequest.getTitle() != null){
            return this.bookRepository.findByTitle(getBooksRequest.getTitle());
        }else if(getBooksRequest.getGenre() != null){
            return this.bookRepository.findByGenre(getBooksRequest.getGenre());
        }

        return new ArrayList<>();
    }
}
