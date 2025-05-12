package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "types_mouvement_stock")

public class TypeMouvementStock extends MultiTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 10)
    private String typeMouvement;

//    @OneToMany(mappedBy = "typeMouvement")
//    @JsonIgnore
//    private Set<TypeMouvementStock> typeMouvementStocks;



}

