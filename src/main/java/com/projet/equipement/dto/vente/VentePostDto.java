package com.projet.equipement.dto.vente;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentePostDto {

    @NotNull
    private Double montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    @NotNull
    private Long clientId;
    @NotNull

    @NotNull
    private Long employeId;

    private Boolean actif ;

}
