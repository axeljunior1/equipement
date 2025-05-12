package com.projet.equipement.mapper;

import com.projet.equipement.dto.roleEmploye.RoleEmployeGetDto;
import com.projet.equipement.dto.roleEmploye.RoleEmployePostDto;
import com.projet.equipement.dto.roleEmploye.RoleEmployeUpdateDto;
import com.projet.equipement.entity.RoleEmploye;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, EmployeMapper.class})
public interface RoleEmployeMapper {


    RoleEmployeGetDto toDto(RoleEmploye roleEmploye);

    RoleEmploye toEntity(RoleEmployeGetDto roleEmployeGetDto);

    RoleEmploye toEntity(RoleEmployePostDto roleEmployeGetDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( RoleEmployeUpdateDto roleEmployeUpdateDto, @MappingTarget RoleEmploye roleEmploye);



}
