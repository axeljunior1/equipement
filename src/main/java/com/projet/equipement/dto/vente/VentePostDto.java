package com.projet.equipement.dto.vente;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentePostDto {

    private Integer montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    private Long clientId;

    private Long employeId;

}
