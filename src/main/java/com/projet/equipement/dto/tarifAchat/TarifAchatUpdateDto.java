package com.projet.equipement.dto.tarifAchat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarifAchatUpdateDto {

    private BigDecimal prixAchat;

}
