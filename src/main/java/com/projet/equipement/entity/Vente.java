package com.projet.equipement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventes")
public class Vente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_ventes")
    private Long id;
    @Column(name = "montant_total")
    private Integer montantTotal;

    @Column(name = "created_at")
    private LocalDateTime dateCreation;

    @Column(name = "updated_at")
    private LocalDateTime dateDerniereMiseAJour;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vente")
    private Set<LigneVente> ligneVentes;



}

