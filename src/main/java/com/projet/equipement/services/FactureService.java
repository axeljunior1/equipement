package com.projet.equipement.services;

import com.projet.equipement.dto.facture.FactureGetDTO;
import com.projet.equipement.dto.facture.FacturePostDTO;
import com.projet.equipement.dto.facture.FactureUpdateDTO;
import com.projet.equipement.entity.Facture;
import com.projet.equipement.mapper.FactureMapper;
import com.projet.equipement.repository.FactureRepository;
import com.projet.equipement.utils.FactureNumeroGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureMapper factureMapper;

    // Création d'une nouvelle facture
    public FactureGetDTO createFacture(FacturePostDTO facturePostDTO) {

        String numeroFacture = FactureNumeroGenerator.generateNumeroFacture();


        // Convertir le DTO en entité
        Facture facture = factureMapper.toEntity(facturePostDTO);

        facture.setNumeroFacture(numeroFacture);

        // Sauvegarder la facture
        facture = factureRepository.save(facture);

        // Convertir l'entité en DTO et retourner
        return factureMapper.toDto(facture);
    }

    // Mettre à jour une facture existante
    public FactureGetDTO updateFacture(Long factureId, FactureUpdateDTO factureUpdateDTO) {
        Facture existingFacture = factureRepository.findById(factureId)
                .orElseThrow(() -> new IllegalArgumentException("Facture not found"));

        // Mettre à jour les informations de la facture
        factureMapper.updateDto(factureUpdateDTO, existingFacture);

        // Sauvegarder la facture mise à jour
        existingFacture = factureRepository.save(existingFacture);

        // Convertir l'entité mise à jour en DTO et retourner
        return factureMapper.toDto(existingFacture);
    }

    // Obtenir une facture par son ID
    public FactureGetDTO getFactureById(Long factureId) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new IllegalArgumentException("Facture not found"));

        return factureMapper.toDto(facture);
    }

    // Obtenir une liste paginée de toutes les factures
    public Page<FactureGetDTO> getAllFactures(Pageable pageable) {
        // Récupérer une page de factures à partir du repository
        Page<Facture> facturePage = factureRepository.findAll(pageable);

        // Convertir la page d'entités Facture en une page de DTOs FactureGetDTO
        return facturePage.map(factureMapper::toDto);
    }

}
