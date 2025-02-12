package com.projet.equipement.mapper;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.Employe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = EmployeMapper.class)
public interface AchatMapper {


    @Mapping(source = "employe", target = "employeId", qualifiedByName = "mapEmployeToId")
    AchatGetDto toDto(Achat achat);

    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    Achat toEntity(AchatGetDto achatGetDto);

    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    Achat toEntity(AchatPostDto achatGetDto);


    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    void updateDto( AchatUpdateDto achatUpdateDto, @MappingTarget Achat achat);


    @Named("mapEmployeToId")
    default Long mapEmployeToId(Employe employe){
        return employe.getId();
    }

    @Named("mapIdToEmploye")
    default Employe mapIdToEmploye(Long id){
        Employe employe = new Employe();
        employe.setId(id);
        return employe;
    }


}
