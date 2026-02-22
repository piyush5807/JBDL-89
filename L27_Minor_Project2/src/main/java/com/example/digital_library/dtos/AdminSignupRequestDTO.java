package com.example.digital_library.dtos;

import com.example.digital_library.models.Admin;
import com.example.digital_library.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class AdminSignupRequestDTO extends SignupRequestDTO{

    private String country;

    public Admin toAdmin(){
        return Admin.builder()
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }


}
