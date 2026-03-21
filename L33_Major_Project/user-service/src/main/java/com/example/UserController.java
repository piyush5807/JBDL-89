package com.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    /**
     * // 127.0.0.1: 8080
     *
     *         // wallet service - 127.0.0.1: 9000
     *
     *         // API call / Kafka events
     *         // RestTemplate -
     *         // WebClient -
     *
     *         // Internal Load balancer - ILB (Service discovery) -
     *         //      prod.red.oms -  10.20.0.100 , 10.20.0.101
     *         //      prod.red.ams -  10.20.0.200, 10.20.0.201
     *         // API Gateway / Load balancer (GLB) -  red.health
     * @param user
     * @return
     */

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public AbstractUser createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        return this.userService.createUser(createUserRequestDTO);
    }

    @GetMapping("/users/internal/{username}")  // permit all
    // txn service --> user-service // unsecured
    public UserDetails getUserInternal(@PathVariable("username") String username) {
        return this.userService.loadUserByUsername(username);
    }

    @GetMapping("/users")
    public UserDetails getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AbstractUser user = (AbstractUser)authentication.getPrincipal();
        return this.userService.loadUserByUsername(user.getUsername());
    }
}
