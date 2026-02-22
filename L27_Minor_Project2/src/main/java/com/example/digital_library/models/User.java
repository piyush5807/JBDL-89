package com.example.digital_library.models;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private static final String AUTHORITIES_DELIMITER = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;
    private String authorities;

    @OneToOne
    @JoinColumn
    private Student student;

    @OneToOne
    @JoinColumn
    private Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = this.authorities.split(AUTHORITIES_DELIMITER);
        return Arrays
                .stream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
