package com.example.demo_jpa;

import lombok.*;

import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Book {

    private Integer id; // ISBN

    private String bookName; // Intro to C++ - Volume 1  , Intro to C++ - Volume 2
    private String bookAuthorName;
    private Genre genre;
    private boolean isAvailable;
    private String publisherName;

    private Date createdAt;
    private Date updatedAt;
}