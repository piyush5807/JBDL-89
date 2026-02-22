package com.example.digital_library.controllers;

import com.example.digital_library.dtos.InitiateTransactionRequest;
import com.example.digital_library.models.Transaction;
import com.example.digital_library.models.User;
import com.example.digital_library.services.TransactionService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/initiate")
    public String initiateTransaction(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) throws Exception {

        // TODO: Fetch the user id from the authentication context instead of taking in the request body to avoid any vulnerability

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();

        return this.transactionService.initiateTransaction(initiateTransactionRequest, user.getStudent());
    }
}
