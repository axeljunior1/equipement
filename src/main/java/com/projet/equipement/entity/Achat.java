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
@Table(name = "achats")
public class Achat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_achat")
    private Long id;

    @Column(name = "montant_total")
    private Double montantTotal;

    @Column(name = "updated_at")
    private LocalDateTime dateDerniereMiseAJour;

    @Column(name = "actif")
    private Boolean actif;

    @Column(name = "created_at")
    private LocalDateTime dateCreation;

    @ManyToOne()
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achat")
    private Set<LigneAchat> ligneAchats;


}

