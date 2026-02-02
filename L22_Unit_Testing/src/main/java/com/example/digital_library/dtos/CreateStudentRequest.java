package com.example.digital_library.dtos;

import com.example.digital_library.models.Address;
import com.example.digital_library.models.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateStudentRequest {

    @NotBlank
    private String name; // not null + not empty

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String rollNumber;

    @Min(10)
    @Max(22)
    private Integer age;

    private String street;
    private String city;
    private String state;
    private String country;

    public Student to(){

        return Student.builder()
                .email(this.email)
                .name(this.name)
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
