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
    private Long id;
    private String nom;
    private String description;
    private String image;
    @Column(name = "prix_unitaire")
    private String prixUnitaire;

    @Column(name = "stock_initial")
    private String quantity;


}
