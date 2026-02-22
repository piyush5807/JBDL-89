package com.example.digital_library;

import com.example.digital_library.dtos.SignupRequestDTO;
import com.example.digital_library.models.Book;
import com.example.digital_library.repositories.BookRepository;
import com.example.digital_library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DigitalLibraryApplication implements CommandLineRunner{

    @Autowired
    UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        try {
            this.userService.createUser(
                    SignupRequestDTO.builder()
                            .name("Bharath")
                            .email("bharath@google.com")
                            .password("bharath@123")
                            .username("bharath123")
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
