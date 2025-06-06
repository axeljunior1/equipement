package com.projet.equipement.entity.payapp;

import com.projet.equipement.entity.MultiTenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WALLET")
public class Wallet extends MultiTenantEntity {
    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal balance;
}
