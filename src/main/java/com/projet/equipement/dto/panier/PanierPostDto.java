package com.projet.equipement.dto.panier;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanierPostDto {




    private Long employeId;

    private Long etatId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();



}
