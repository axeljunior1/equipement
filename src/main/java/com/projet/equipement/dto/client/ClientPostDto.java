package com.projet.equipement.dto.client;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {
    private String nom;
    private String pronom;
    private String email;
    private String telephone;

    private LocalDateTime dateCreation;
}
