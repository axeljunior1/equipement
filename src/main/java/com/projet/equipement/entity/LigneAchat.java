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
public class LigneAchat extends MultiTenantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lignes_achat")
    private Long id;

    @Column(name = "prix_achat")
    private Double prixAchat;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "actif")
    @Builder.Default
    private Boolean actif = true;

    @ManyToOne
    @JoinColumn(name = "achat_id")
    @JsonIgnore
    private Achat achat;

    @ManyToOne()
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;


}

