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
@Table(name = "ligne_retour")
public class LigneRetour extends MultiTenantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "ligne_vente_id")
    @JsonIgnore
    private LigneVente ligneVente;

    @ManyToOne()
    @JoinColumn(name = "retour_id")
    private Retour retour;



}

