package com.example.demo_jpa;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    private String bookName; // Intro to C++ - Volume 1  , Intro to C++ - Volume 2
    private String bookAuthorName;
    private Genre genre;
    private boolean isAvailable;
    private String publisherName;

    public Book to(){

//        Book book = new Book();
//        book.setBookName(this.bookName);
//        book.setBookAuthorName(this.bookAuthorName);
//        book.setGenre(this.genre);
//        book.setAvailable(this.isAvailable);
//        book.setPublisherName(this.publisherName);

        return Book.builder()
                .bookName(this.bookName)
                .bookAuthorName(this.bookAuthorName)
                .genre(this.genre)
                .isAvailable(this.isAvailable)
                .publisherName(this.publisherName)
//                .createdAt(new Date())
//                .updatedAt(new Date())
                .build();
    }
}
