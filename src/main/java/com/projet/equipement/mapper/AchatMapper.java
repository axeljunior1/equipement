package com.projet.equipement.mapper;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.Employe;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = EmployeMapper.class)
public interface AchatMapper {


    /**
     * Maps an Achat entity to an AchatGetDto.
     *
     * @param achat achat entity to be mapped
     * @return the mapped AchatGetDto
     */
    @Mapping(source = "employe", target = "employeId", qualifiedByName = "mapEmployeToId")
    AchatGetDto toDto(Achat achat);


    /**
     * Maps an AchatPostDto to an Achat entity.
     * Uses the `mapIdToEmploye` method to convert the employeId in AchatPostDto
     * to the corresponding Employe entity in Achat.
     *
     * @param achatGetDto the AchatPostDto object containing the data to be mapped
     * @return the mapped Achat entity
     */
    Achat toEntity(AchatPostDto achatGetDto);


    /**
     * Updates an existing Achat entity with the values provided in an AchatUpdateDto.
     * The mapping supports converting the employeId in the AchatUpdateDto to the Employe
     * entity associated with the Achat.
     *
     * @param achatUpdateDto the data transfer object containing the updated values for the Achat
     * @param achat the target Achat entity to be updated
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( AchatUpdateDto achatUpdateDto, @MappingTarget Achat achat);


    /**
     * Maps an Employe entity to its identifier.
     *
     * @param employe the Employe entity to be mapped
     * @return the identifier of the Employe
     */
    @Named("mapEmployeToId")
    default Long mapEmployeToId(Employe employe){
        return employe.getId();
    }

    /**
     * Maps a given identifier to an Employe entity.
     *
     * @param id the identifier of the Employe
     * @return an Employe entity with its id field populated
     */



}
