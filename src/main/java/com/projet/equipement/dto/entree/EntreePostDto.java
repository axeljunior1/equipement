package com.projet.equipement.dto.entree;

import com.projet.equipement.entity.Fournisseur;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntreePostDto {

    @NotNull(message = "Quantité ne peut pas être null")
    private Integer quantite;

    @NotNull(message = "Prix d'achat ne peut pas être null")
    private Double prixAchat;

    @NotNull(message = "Produit ID ne peut pas être null")
    private Long produitId;

    @NotNull(message = "Fournisseur ID ne peut pas être null")
    private Long fournisseurId;

    @NotNull(message = "Utilisateur ID ne peut pas être null")
    private Long utilisateurId;




}
