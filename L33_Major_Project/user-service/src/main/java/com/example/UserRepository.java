package com.example;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AbstractUser, Integer> {

    AbstractUser findByUsername(String username);
}
