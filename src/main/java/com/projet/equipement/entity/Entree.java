package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ENTREES")
public class Entree {
    @Id
    private Long id_entree;
    private int quantite;
    private String date_entree;
    private double prix_achat;
    @OneToOne
    @JoinColumn(name = "ID_PRODUIT")
    private Produit produit;
    @OneToOne
    @JoinColumn(name = "ID_FOURNISSEUR")
    private Fournisseur fournisseur;
}

