package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement")
    private Long idPaiement;

    @ManyToOne
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture;

    @Column(name = "montant_paye", nullable = false)
    private BigDecimal montantPaye;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement = LocalDateTime.now();

    @Column(name = "mode_paiement")
    private String modePaiement;

    @Column(name = "reference")
    private String reference;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
