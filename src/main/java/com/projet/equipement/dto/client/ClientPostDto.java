package com.projet.equipement.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {
    @NotBlank(message = "Le nom est vide ")
    private String nom;

    @NotNull(message = "Le nom est vide ")
    private String prenom;
    private String email;
    private String telephone;

    @Builder.Default
    private Boolean actif = true;

    private LocalDateTime dateCreation;
}
