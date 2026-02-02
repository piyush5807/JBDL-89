package com.example.digital_library.repositories;

import com.example.digital_library.models.Author;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByEmail(String email); // => select * from author where email = ?

//    List<Author> findByNameLike(String regex); // select * from author where name like

}
