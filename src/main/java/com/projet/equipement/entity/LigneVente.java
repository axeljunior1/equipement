package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lignes_ventes")
public class LigneVente extends MultiTenantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lignes_ventes")
    private Long id;

    @Column(name = "prix_vente_unitaire")
    private Double prixVente;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "actif")
    @Builder.Default
    private Boolean actif = true;

    @ManyToOne
    @JoinColumn(name = "vente_id")
    @JsonIgnore
    private Vente vente;

    @ManyToOne()
    @JoinColumn(name = "produit_id")
    private Produit produit;


    @ManyToOne()
    @JoinColumn(name = "format_vente_id")
    @JsonIgnore
    private FormatVente formatVente;

    @OneToMany(mappedBy = "ligneVente")
    private List<Retour> retours;

}

