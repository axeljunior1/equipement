package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="MOUVEMENTS_STOCK")
public class MouvementStock {
    @Id
    @GeneratedValue
    @Column(name = "ID_MOUVEMENT")
    private Long  id_mvt;

    @OneToOne
    @JoinColumn(name = "ID_PRODUIT")
    private Produit produit ;

    @OneToOne
    @JoinColumn(name = "ID_UTILISATEUR")
    private Utilisateur utilisateur ;


    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_MOUVEMENT")
    private TypeMouvement typeMouvement ;

    @Column(name = "QUANTITE")
    private Integer quantity ;





}

