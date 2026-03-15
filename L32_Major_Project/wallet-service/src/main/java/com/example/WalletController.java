package com.example;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/wallets/{walletId}")
    public Wallet getWallet(@PathVariable("walletId") Integer walletId){
        return walletService.get(walletId);
    }
}
