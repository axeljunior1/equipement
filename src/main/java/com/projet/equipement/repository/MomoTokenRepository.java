package com.projet.equipement.repository;

import com.projet.equipement.entity.MomoToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MomoTokenRepository extends JpaRepository<MomoToken, Long> {
    Optional<MomoToken> findTopByOrderByCreatedAtDesc();
}
