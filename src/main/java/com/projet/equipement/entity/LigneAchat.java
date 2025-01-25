package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lignes_achats")
public class LigneAchat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lignes_achat")
    private Long id;

    @Column(name = "prix_achat")
    private Integer prixAchatUnitaire;

    @Column(name = "quantite")
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "achat_id")
    @JsonIgnore
    private Achat achat;

    @ManyToOne()
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;



}

