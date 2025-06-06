package com.projet.equipement.repository.payapp;

import com.projet.equipement.entity.payapp.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
