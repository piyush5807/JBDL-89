package com.example.digital_library.dtos;

import com.example.digital_library.models.Genre;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBooksRequest {


    @Email
    private String authorEmail;

    private String title;

    private Genre genre;
}
