package com.projet.equipement.services.payapp;

import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.entity.payapp.PaymentTransaction;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.payapp.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;

    public PaymentTransactionService(PaymentTransactionRepository transactionRepository  ) {
        this.transactionRepository = transactionRepository;
    }

    public PaymentTransaction createTransaction(PaymentTransaction tx) {
        tx.setTenantId(TenantContext.getTenantId());
        tx.setStatus("PENDING");
        return transactionRepository.save(tx);
    }

    public PaymentTransaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Transaction", id));
    }

    public List<PaymentTransaction> getAllForTenant() {
        return transactionRepository.findAll();
    }

    public void markSuccess(Long id) {
        var tx = transactionRepository.findById(id).orElseThrow();
        tx.setStatus("SUCCESS");
        transactionRepository.save(tx);
    }

    public void markFailed(Long id) {
        var tx = transactionRepository.findById(id).orElseThrow();
        tx.setStatus("FAILED");
        transactionRepository.save(tx);
    }
}
