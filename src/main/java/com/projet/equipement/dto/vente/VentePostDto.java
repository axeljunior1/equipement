package com.projet.equipement.dto.vente;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VentePostDto {

    private Integer montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    private Long clientId;

    private Long employeId;

}
