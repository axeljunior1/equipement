package com.projet.equipement.services;

import com.projet.equipement.dto.facture.FactureGetDTO;
import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.entity.EtatVente;
import com.projet.equipement.entity.Paiement;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.enumeration.PaiementEnum;
import com.projet.equipement.enumeration.PaiementTransitionEnum;
import com.projet.equipement.enumeration.VenteTransitionEnum;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.PaiementMapper;
import com.projet.equipement.repository.EtatPaiementRepository;
import com.projet.equipement.repository.PaiementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;
    private final StateMachine<PaiementEnum, String> stateMachine;
    private final EtatPaiementRepository etatPaiementRepository;
    private final FactureService factureService;

    public PaiementService(PaiementRepository paiementRepository, PaiementMapper paiementMapper, StateMachine<PaiementEnum, String> stateMachine, EtatPaiementRepository etatPaiementRepository, FactureService factureService) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
        this.stateMachine = stateMachine;
        this.etatPaiementRepository = etatPaiementRepository;
        this.factureService = factureService;
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
                .orElseThrow(() -> new EntityNotFoundException("Paiement", paiementId));

        // Mettre à jour les informations du paiement
        paiementMapper.updateDto(paiementUpdateDTO, existingPaiement);


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


    // State Machine

//
//
//    EN_ATTENTE_PARTIEL
//            PARTIEL_EFFECTUE

    public void payerTotal(Long id) {

        Paiement paiement = paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_EFFECTUE));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatPaiement etat = etatPaiementRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatPaiement", libelle));

        paiement.setEtat(etat);
        paiementRepository.save(paiement);
    }

    public void payerPartiel(Long id) {

        Paiement paiement = paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_PARTIEL));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatPaiement etat = etatPaiementRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatPaiement", libelle));

        paiement.setEtat(etat);
        paiementRepository.save(paiement);
    }

    public void payerPartielTotal(Long id) {

        Paiement paiement = paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(PaiementTransitionEnum.PARTIEL_EFFECTUE));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatPaiement etat = etatPaiementRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatPaiement", libelle));

        paiement.setEtat(etat);
        paiementRepository.save(paiement);
    }

    public void annuler(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_REFUSE));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatPaiement etat = etatPaiementRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatPaiement", libelle));

        paiement.setEtat(etat);
        paiementRepository.save(paiement);
    }


    public void rembourser(Long id) {
        Paiement paiement = paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(PaiementTransitionEnum.EN_ATTENTE_EFFECTUE));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatPaiement etat = etatPaiementRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatPaiement", libelle));

        paiement.setEtat(etat);
        paiementRepository.save(paiement);
    }

}
