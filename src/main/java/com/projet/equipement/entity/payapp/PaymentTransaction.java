package com.projet.equipement.entity.payapp;

import com.projet.equipement.entity.MultiTenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENT_TRANSACTION")
@Entity
public class PaymentTransaction extends MultiTenantEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "CLIENT_PHONE")
    private String clientPhone;
    private BigDecimal amount;
    private String status; // PENDING, SUCCESS, FAILED

    private LocalDateTime timestamp;

}


