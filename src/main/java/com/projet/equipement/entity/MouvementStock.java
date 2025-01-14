package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="mouvements_stock")

public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mouvement")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit ;

    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur ;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_mouvemnt")
    private TypeMouvement typeMouvement ;

    @Column(name = "quantite")
    private Integer quantity ;

    @OneToOne
    @JoinColumn(name = "id_entree", nullable = true) // Relie un MouvementStock à une Entree
    private Entree entree;

    @OneToOne
    @JoinColumn(name = "id_sortie", nullable = true) // Relie un MouvementStock à une Entree
    private Sortie sortie;


}

