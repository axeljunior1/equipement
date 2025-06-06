package com.projet.equipement.controller;

import com.projet.equipement.entity.payapp.Wallet;
import com.projet.equipement.services.payapp.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")

public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getWallets() {
        return walletService.getWallets();
    }

    @GetMapping("/{id}")
    public Wallet getUserById(Long id) {
        return walletService.getWalletById(id);
    }

    // modifier le balance du wallet
    @PutMapping("/{id}")
    public String addAmont(Long id, BigDecimal balance) {
         walletService.addAmount(balance, id);
         return "Balance updated";
    }

    @PutMapping("/{id}/remove")
    public String removeAmont(Long id, BigDecimal balance) {
        walletService.subtractAmount(balance, id);
        return "Balance updated";
    }
}
