package com.projet.equipement.dto.devise;

import com.projet.equipement.dto.employe.EmployeGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeviseGetDto {


    private Long id ;

    private String code;     // Ex: USD, EUR

    private String nom;      // Ex: Dollar américain

    private String symbole;  // Ex: $

}
