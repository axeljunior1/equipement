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
@Table(name = "clients")
public class Client {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_client")
    private Long id;
    private String nom;
    private String prenom;
    @Column()
    private String email;
    private String telephone;


    @Column(name = "created_at")
    private LocalDateTime dateCreation;

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private Vente vente;



}

