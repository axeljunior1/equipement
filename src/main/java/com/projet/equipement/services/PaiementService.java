package com.projet.equipement.services;

import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.entity.Paiement;
import com.projet.equipement.mapper.PaiementMapper;
import com.projet.equipement.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;

    public PaiementService(PaiementRepository paiementRepository, PaiementMapper paiementMapper) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
    }

    // Créer un paiement
    public PaiementGetDTO createPaiement(PaiementPostDTO paiementPostDTO) {
        // Convertir le DTO en entité
        Paiement paiement = paiementMapper.toEntity(paiementPostDTO);

        // Sauvegarder le paiement
        paiement = paiementRepository.save(paiement);

        // Convertir l'entité en DTO et retourner
        return paiementMapper.toDto(paiement);
    }

    // Mettre à jour un paiement existant
    public PaiementGetDTO updatePaiement(Long paiementId, PaiementUpdateDTO paiementUpdateDTO) {
        Paiement existingPaiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new IllegalArgumentException("Paiement not found"));

        // Mettre à jour les informations du paiement
        paiementMapper.updateDto(paiementUpdateDTO, existingPaiement);

        // Sauvegarder le paiement mis à jour
        existingPaiement = paiementRepository.save(existingPaiement);

        // Convertir l'entité mise à jour en DTO et retourner
        return paiementMapper.toDto(existingPaiement);
    }

    // Obtenir un paiement par son ID
    public PaiementGetDTO getPaiementById(Long paiementId) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new IllegalArgumentException("Paiement not found"));

        return paiementMapper.toDto(paiement);
    }

    // Obtenir une liste paginée de tous les paiements
    public Page<PaiementGetDTO> getAllPaiements(int page, int size) {
        // Créer un objet Pageable pour la pagination
        Pageable pageable = PageRequest.of(page, size);

        // Récupérer une page de paiements à partir du repository
        Page<Paiement> paiementPage = paiementRepository.findAll(pageable);

        // Convertir la page d'entités Paiement en une page de DTOs PaiementGetDTO
        return paiementPage.map(paiementMapper::toDto);
    }

    // Obtenir une liste paginée de paiements par mode de paiement
    public Page<PaiementGetDTO> getPaiementsByModePaiement(String modePaiement, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Récupérer une page de paiements avec le mode de paiement spécifié
        Page<Paiement> paiementPage = paiementRepository.findByModePaiement(modePaiement, pageable);

        // Convertir la page d'entités Paiement en une page de DTOs PaiementGetDTO
        return paiementPage.map(paiementMapper::toDto);
    }

    // Autres méthodes de service, selon vos besoins...
}
