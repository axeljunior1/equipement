package com.projet.equipement.mapper;

import com.projet.equipement.dto.etatDto.EtatAchatGetDto;
import com.projet.equipement.entity.EtatAchat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface EtatAchatMapper {

    EtatAchatGetDto toGetDto(EtatAchat etatAchat) ;

}
