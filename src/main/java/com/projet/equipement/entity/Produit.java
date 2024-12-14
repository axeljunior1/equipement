package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long id;

    private String nom;
    private String description;
    private String image;
    @Column(name = "prix_unitaire")
    private int prixUnitaire;

    @Column(name = "stock_initial")
    private int quantity;


}
