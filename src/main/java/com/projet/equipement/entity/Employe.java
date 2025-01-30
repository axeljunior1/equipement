package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private String password;


    @Column(name = "created_at")
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Vente> ventes;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Achat> achats;



}


