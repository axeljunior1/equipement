package com.projet.equipement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "utilisation_avoir")
public class UtilisationAvoir extends MultiTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "avoir_id", nullable = false)
    private Avoir avoir;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @Column(name = "montant_utilise", nullable = false)
    private BigDecimal montantUtilise;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
