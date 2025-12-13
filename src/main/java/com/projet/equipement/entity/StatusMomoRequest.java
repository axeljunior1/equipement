package com.projet.equipement.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusMomoRequest {


    @NotNull
    private String refId;

    @NotNull
    private Long venteId;

}
