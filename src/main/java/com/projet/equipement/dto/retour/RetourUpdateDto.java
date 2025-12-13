package com.projet.equipement.dto.retour;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class RetourUpdateDto {


    private Long venteId;

    private Long typeId;

    private Long etatId;

    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();

}
