package com.example.digital_library.controllers;

import com.example.digital_library.dtos.InitiateTransactionRequest;
import com.example.digital_library.models.Transaction;
import com.example.digital_library.services.TransactionService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/initiate")
    public String initiateTransaction(@RequestBody @Valid InitiateTransactionRequest initiateTransactionRequest) throws BadRequestException {
        return this.transactionService.initiateTransaction(initiateTransactionRequest);
    }
}
