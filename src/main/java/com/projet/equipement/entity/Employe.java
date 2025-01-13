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
@Table(name = "employes")
public class Employe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_employe")
    private Long id;
    private String nom;
    private String prenom;


    @Column(name = "created_at")
    private LocalDateTime dateCreation;

    @OneToOne(mappedBy = "employe")
    @JsonIgnore
    private Vente vente;

    @OneToOne(mappedBy = "employe")
    @JsonIgnore
    private Achat achat;



}


