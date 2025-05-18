package com.projet.equipement.mapper;

import com.projet.equipement.dto.role.RoleGetDto;
import com.projet.equipement.dto.role.RolePostDto;
import com.projet.equipement.dto.role.RoleUpdateDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.AuthorityRole;
import com.projet.equipement.entity.Role;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = Authority.class)
public interface RoleMapper {


    @Mapping(source = "authoritiesRole", target = "authorityIds", qualifiedByName = "mapAuthorityToId")
    @Mapping(source = "authoritiesRole", target = "authorityNoms", qualifiedByName = "mapAuthorityToNoms")
    @Mapping(source = "authoritiesRole", target = "authorities", qualifiedByName = "mapAuthToAuth")
    RoleGetDto toGetDto(Role role) ;

    Role toEntity(RoleGetDto roleGetDto);

    Role toEntity(RolePostDto rolePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromDto(RoleUpdateDto roleUpdateDto, @MappingTarget Role role);

    @Named("mapAuthorityToId")
    default Set<Long> mapAuthorityToId(Set<AuthorityRole> authoritiesRole) {
        return authoritiesRole.stream().map(AuthorityRole::getAuthority)
                .map(Authority::getId)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Named("mapAuthorityToNoms")
    default Set<String> mapAuthorityToNoms(Set<AuthorityRole> authorityRoles){
        return authorityRoles.stream().map(AuthorityRole::getAuthority)
                .map(Authority::getNom)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Named("mapAuthToAuth")
    default Set<Authority> mapAuthToAuth(Set<AuthorityRole> authorities){
        return authorities.stream().map(AuthorityRole::getAuthority)
                .collect(java.util.stream.Collectors.toSet());
    }

}
