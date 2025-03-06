package com.projet.equipement.dto.tarifAchat;


import com.projet.equipement.dto.produit.ProduitGetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarifAchatGetDto {

    private ProduitGetDto produit;

    private BigDecimal prixAchat;

}
