package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "retour")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Retour extends MultiTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "date_creation")
    private LocalDateTime dateCreation;


    @ManyToOne
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @ManyToOne
    @JoinColumn(name = "type_retour_id", nullable = false)
    private TypeRetour typeRetour;

    @ManyToOne
    @JoinColumn(name = "etat_retour_id", nullable = false)
    private EtatRetour etat;


}
