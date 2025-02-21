package com.projet.equipement.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facture")
    private Long idFacture;

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente; // Assurez-vous d'avoir la classe Vente avec la relation appropriée

    @Column(name = "numero_facture", unique = true, nullable = false)
    private String numeroFacture;

    @Builder.Default
    @Column(name = "date_facture", nullable = false)
    private LocalDateTime dateFacture = LocalDateTime.now();

    @Column(name = "montant_total", nullable = false)
    private BigDecimal montantTotal;

    @Builder.Default
    @Column(name = "statut", nullable = false)
    private String statut = "En attente";

    @Column(name = "mode_paiement")
    private String modePaiement;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
