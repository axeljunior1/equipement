package com.projet.equipement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodeDTO {
    private LocalDateTime start;
    private LocalDateTime end;
}
