package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Builder.Default
    private Boolean actif = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_employe",
            joinColumns = @JoinColumn(name = "id_employe"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    @NotNull(message = "Passer le(s) role(s)" )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime dateCreation =LocalDateTime.now();

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Vente> ventes;

    @OneToMany(mappedBy = "employe")
    @JsonIgnore
    private List<Achat> achats;



}


