package com.projet.equipement.mapper;

import com.projet.equipement.dto.devise.DeviseGetDto;
import com.projet.equipement.dto.devise.DevisePostDto;
import com.projet.equipement.dto.devise.DeviseUpdateDto;
import com.projet.equipement.entity.Devise;
import com.projet.equipement.entity.Employe;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = EmployeMapper.class)
public interface DeviseMapper {


    DeviseGetDto toDto(Devise devise);


    Devise toEntity(DeviseGetDto deviseGetDto);


    Devise toEntity(DevisePostDto deviseGetDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeviseFromDto( DeviseUpdateDto deviseUpdateDto, @MappingTarget Devise devise);



}
