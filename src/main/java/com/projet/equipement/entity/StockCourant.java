package com.projet.equipement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vue_stock_courant")
public class StockCourant extends MultiTenantEntity {
    @Id
    @Column(name = "id_produit")
    private Long id;

    @Column(name = "stock_courant")
    private Integer stockCourant;
}
