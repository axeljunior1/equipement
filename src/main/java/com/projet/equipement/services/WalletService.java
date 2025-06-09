package com.projet.equipement.services;

import com.projet.equipement.entity.Wallet;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }



    public void addAmount(BigDecimal amount, Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Wallet", id));
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }
    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Wallet", id));
    }

    public BigDecimal getBalance(Long id) {
        return walletRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Wallet", id)).getBalance();
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void subtractAmount(BigDecimal amount, Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Wallet", id));
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
    }

    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }
}
