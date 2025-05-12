package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "retour")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Retour extends MultiTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    private int quantité;

    private LocalDateTime dateRetour;

    private String raison; // facultatif : produit défectueux, erreur, etc.

    @ManyToOne
    @JoinColumn(name = "ligne_id", nullable = false)
    private LigneVente ligneVente;

    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;


}
