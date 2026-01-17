package com.example.digital_library.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Transaction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne  // Mostly the foreign keys are not one to many
    @JoinColumn
    private Admin createdBy;

    @ManyToOne
    @JoinColumn
    private Author author;

    @ManyToOne
    @JoinColumn
    private Student student; // student who possess the book currently

//    @OneToMany
//    private List<Transaction> transactionList; // this is not going to be a column in my book table

    /**
     * JPA relationships:
     * 1. Unidirectional relationship: You add the model linked to the current entity
     * only when that model is added a foreign in the current entity's table

     * 2. Bidirectional relationship: You add the both the models linked to each other irrespective
     * of whether foreign key column is present for the linked model or not
     *
     * OneToOne
     * OneToMany
     * ManyToOne
     * ManyToMany
     */

}
