package com.projet.equipement.dto.ligneAchat;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatGetDto {

    private Long id ;

    private Double prixUnitaire;

    private Integer quantite;

    private Long achatId;

    private AchatGetDto achat;

    private Boolean actif;

    private Long produitId;

    private ProduitGetDto produit;

    private String  produitNom;


}
