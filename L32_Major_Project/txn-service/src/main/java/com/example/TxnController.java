package com.example;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/transactions")
    public String initiateTxn(@Valid @RequestBody InitiateTxnRequestDTO initiateTxnRequestDTO) {
        return this.txnService.initiate(initiateTxnRequestDTO);
    }

    /**
     * Pending things for tomorrow
     * - P0 - Add security in txn service just like we did in user-service but in a different way so that we don't duplicate user tables
     * - P1 - @Transactional in service level functions where we are doing multiple db calls
     * - P2 - Notification Service - Email
     */

}
