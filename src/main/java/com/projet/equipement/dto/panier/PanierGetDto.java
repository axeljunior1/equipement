package com.projet.equipement.dto.panier;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.Etat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PanierGetDto {


    private Long id ;

    private EmployeGetDto employe;

    private Etat etat;

    private LocalDateTime createdAt;



}
