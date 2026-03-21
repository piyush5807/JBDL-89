package com.example;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateUserRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public AbstractUser to(){
        return AbstractUser.builder()
                .name(name)
                .email(email)
                .username(username)
                .password(password)
                .build();
    }


}
