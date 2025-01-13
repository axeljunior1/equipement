package com.projet.equipement.dto.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientPostDto {
    private String nom;
    private String pronom;
    private String email;
    private String telephone;

    private LocalDateTime dateCreation;
}
