package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lignes_ventes")
public class LigneVente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lignes_ventes")
    private Long id;

    @Column(name = "prix_vente_unitaire")
    private Integer prixVenteUnitaire;

    @Column(name = "quantite")
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "vente_id")
    @JsonIgnore
    private Vente vente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produit_id")
    private Produit produit;



}

