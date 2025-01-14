package com.projet.equipement.repository;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNom(String nom);
}
