package com.example.digital_library.dtos;

import com.example.digital_library.models.Address;
import com.example.digital_library.models.Student;
import com.example.digital_library.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class StudentSignupRequestDTO extends SignupRequestDTO {

    @NotBlank
    private String rollNumber;

    @Min(10)
    @Max(22)
    private Integer age;

    private String street;
    private String city;
    private String state;
    private String country;

    public Student toStudent(){
        return Student.builder()
                .email(this.getEmail())
                .name(this.getName())
                .rollNumber(this.rollNumber)
                .age(this.age)
                .address(
                        Address.builder()
                                .city(this.city)
                                .street(this.street)
                                .state(this.state)
                                .country(this.country)
                                .build()
                )
                .build();
    }
}
