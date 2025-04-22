package com.projet.equipement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "etat_id", nullable = false)
    private EtatFacture etat;

    @Column(name = "numero_facture", unique = true, nullable = false)
    private String numeroFacture;

    @Builder.Default
    @Column(name = "date_facture", nullable = false)
    private LocalDateTime dateFacture = LocalDateTime.now();

    @Column(name = "montant_total", nullable = false)
    private BigDecimal montantTotal;

    @Column(name = "montant_restant", nullable = false)
    private BigDecimal montantRestant;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    private List<Paiements> paiements;
}
