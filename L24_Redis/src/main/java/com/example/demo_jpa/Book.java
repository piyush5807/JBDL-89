package com.example.demo_jpa;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // this annotation is used by hibernate to do the ORM (Object relation mapping), without this annotation, this class is not eligible for any interaction with the database via hibernate
@Table(name = "library_book")
public class Book implements Serializable {

    /**
     * IDENTITY GENERATION TYPE is a strategy where underlying database server adds the auto incremented ID by themselves
     */

    @Id // this column 'id' will work as a primary key column for the book table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ISBN

    @Column(length = 100)
    private String bookName; // Intro to C++ - Volume 1  , Intro to C++ - Volume 2
    private String bookAuthorName;
    private Genre genre;
    private Boolean isAvailable;
    private String publisherName;

    @Column(unique = true)
    private String bookCode;

    @CreationTimestamp
    private Date createdAt; // this is automatically populated whenever a new record is added in the table

    @UpdateTimestamp
    private Date updatedAt; // this is automatically going to change whenever either a new record is added in the table or an existing record is updated
}

/**
 * create table library_book (genre tinyint check ((genre between 0 and 4)),
 * id integer not null auto_increment, is_available bit not null, created_at datetime(6),
 * updated_at datetime(6), book_author_name varchar(255),
 * book_name varchar(255), publisher_name varchar(255), primary key (id)) engine=InnoDB
 */

/**
 * create table library_book (genre tinyint check ((genre between 0 and 4)),
 * id integer not null, is_available bit not null, created_at datetime(6),
 * updated_at datetime(6), book_name varchar(100), book_author_name varchar(255),
 * publisher_name varchar(255), primary key (id)) engine=InnoDB
 */