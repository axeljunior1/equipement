package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_client")
    private Long id;
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    private String prenom;

    @Column()
    private String email;



    @Builder.Default
    @Column(name = "actif")
    private Boolean actif = true ;

    @NotBlank(message = "Le telephone doit etre renseigné !")
    private String telephone;


    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt =LocalDateTime.now();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Vente> ventes;



}

