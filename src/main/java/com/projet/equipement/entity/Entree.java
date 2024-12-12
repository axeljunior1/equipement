package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entrees")
public class Entree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_entree;
    private int quantite;
    private String date_entree;
    private double prix_achat;
    @OneToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
    @OneToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseur fournisseur;
}

