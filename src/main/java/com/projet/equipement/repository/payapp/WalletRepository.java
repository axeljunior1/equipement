package com.projet.equipement.repository.payapp;

import com.projet.equipement.entity.payapp.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByBalance(BigDecimal balance);
}
