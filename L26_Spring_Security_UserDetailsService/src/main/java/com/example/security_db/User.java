package com.example.security_db;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private static final String DELIMITER = ":";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username; // UN => PWD

    private String name;

    private String password;
    private String authorities; // [FACULTY, STUDENT] => FACULTY:STUDENT

    // TODO: To use whenever you need these functionalities in your system and accordingly build relevant APIs for locking / disabling and expiring the credentials after certain duration
//    private boolean isAccountNonExpired;
//    private boolean isAccountNonLocked;
//    private boolean isCredentialsNonExpired;
//    private boolean isEnabled;


    /**
     * Authority vs Role ? In context of Spring security
     * Whenever you define user having role of STUDENT, internally spring security considers that
     * user having an authority of ROLE_<ROLE_NAME> i.e ROLE_STUDENT
     *
     * Authority is nothing but ROLE_<role-name>
     * role - x => authority = ROLE_x
     * authority - y => authority = y
     *
     */


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = this.authorities.split(DELIMITER);
        return Arrays
                .stream(authorities)
                .map(x -> new SimpleGrantedAuthority(x))
//                .map( SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
