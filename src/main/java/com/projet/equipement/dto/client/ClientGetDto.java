package com.projet.equipement.dto.client;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientGetDto {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Boolean actif;
    private LocalDateTime dateCreation;

}
