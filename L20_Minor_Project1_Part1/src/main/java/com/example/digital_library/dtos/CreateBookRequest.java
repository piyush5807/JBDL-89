package com.example.digital_library.dtos;

import com.example.digital_library.models.Admin;
import com.example.digital_library.models.Author;
import com.example.digital_library.models.Book;
import com.example.digital_library.models.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookRequest {

    // For a book to be added, we need an admin to exist beforehand, whereas author we can create
    // if not present already on the runtime during book creation

    @NotBlank
    private String title;

    @NotNull
    private Genre genre;

    @NotNull
    private Integer createdBy;

    private String name; // author name

    @NotBlank
    @Email
    private String email;

    public Book to(){
        return Book.builder()
                .title(this.title)
                .genre(this.genre)
                .author(
                        Author.builder()
                                .email(this.email)
                                .name(this.name)
                                .build()
                ).createdBy(
                        Admin.builder()
                                .id(this.createdBy)
                                .build()
                )
                .build();
    }
}
