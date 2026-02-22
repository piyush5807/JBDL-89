package com.example.digital_library.dtos;

import com.example.digital_library.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password; // plain text pwd

    @NotBlank
    private String name; // not null + not empty

    @NotBlank
    @Email
    private String email;

    public User toUser(){
        return User.builder()
                .username(this.getUsername())
                .password(this.getPassword())
                .build();
    }

}
