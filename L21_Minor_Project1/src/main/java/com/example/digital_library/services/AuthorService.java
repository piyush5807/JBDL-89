package com.example.digital_library.services;

import com.example.digital_library.models.Author;
import com.example.digital_library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author addOrGetAuthor(Author author){
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());
        if(authorFromDB == null){
            this.authorRepository.save(author);
            return author;
        }

        author.setId(authorFromDB.getId());
        return authorFromDB;
    }

    public Author addAuthor(Author author){
        return this.authorRepository.save(author); // UK Violation exception if that author already exists
    }
}
