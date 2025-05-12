package com.projet.equipement.dto.roleEmploye;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class RoleEmployePostDto {

    private String tenantId;

}
