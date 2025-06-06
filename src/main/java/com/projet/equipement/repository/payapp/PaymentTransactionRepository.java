package com.projet.equipement.repository.payapp;

import com.projet.equipement.entity.payapp.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
