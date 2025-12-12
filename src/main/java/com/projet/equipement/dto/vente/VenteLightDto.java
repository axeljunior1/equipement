package com.projet.equipement.dto.vente;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VenteLightDto {
    private Long id;
    private Double montantTotal;
    private LocalDateTime createdAt;
    private List<LigneVenteGetDto> ligneVentes;
}
