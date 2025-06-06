package com.projet.equipement.controller;

import com.projet.equipement.entity.payapp.PaymentTransaction;
import com.projet.equipement.services.payapp.PaymentTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/payment-transactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController( PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }
    @GetMapping
    public List<PaymentTransaction> getpayments1() {
        return paymentTransactionService.getAllForTenant();
    }
    @GetMapping("/{id}")
    public PaymentTransaction getPay(Long id) {
        return paymentTransactionService.getTransaction(id);
    }


}
