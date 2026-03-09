package com.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public User createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        return this.userService.createUser(createUserRequestDTO);
    }
}
