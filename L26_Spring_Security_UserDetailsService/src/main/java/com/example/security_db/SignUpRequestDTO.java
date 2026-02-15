package com.example.security_db;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO {

    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
