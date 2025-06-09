package com.projet.equipement.services;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.facture.FacturePostDTO;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.validerPanier.ValiderPanierDTO;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.enumeration.VenteEnum;
import com.projet.equipement.enumeration.VenteEvent;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.*;
import com.projet.equipement.utils.FactureNumeroGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenteService {


    private static final String VENTE = "Vente";
    private static final String ETAT_VENTE = "EtatVente";

    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;
    private final ClientRepository clientRepository;
    private final EmployeRepository employeRepository;
    private final EtatVenteRepository etatVenteRepository;
    private final ClientService clientService;
    private final EmployeService employeService;
    private final StateMachineFactory<VenteEnum, VenteEvent> factory;
    private final PanierService panierService;
    private final PanierProduitService panierProduitService;
    private final LigneVenteService ligneVenteService;
    private final EtatFactureService etatFactureService;
    private final FactureService factureService;
    private final LigneVenteRepository ligneVenteRepository;
    private final EtatPaiementService etatPaiementService;
    private final PaiementService paiementService;

    public VenteService(VenteRepository venteRepository,
                        VenteMapper venteMapper,
                        ClientRepository clientRepository,
                        EmployeRepository employeRepository,
                        EtatVenteRepository etatVenteRepository,
                        ClientService clientService,
                        EmployeService employeService,
                        PanierService panierService,
                        PanierProduitService panierProduitService,
                        LigneVenteService ligneVenteService,
                        EtatFactureService etatFactureService,
                        FactureService factureService,
                        LigneVenteRepository ligneVenteRepository,
                        StateMachineFactory<VenteEnum, VenteEvent> factory,
                        EtatPaiementService etatPaiementService, PaiementService paiementService) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
        this.clientRepository = clientRepository;
        this.employeRepository = employeRepository;
        this.etatVenteRepository = etatVenteRepository;
        this.clientService = clientService;
        this.employeService = employeService;
        this.panierService = panierService;
        this.panierProduitService = panierProduitService;
        this.ligneVenteService = ligneVenteService;
        this.etatFactureService = etatFactureService;
        this.factureService = factureService;
        this.ligneVenteRepository = ligneVenteRepository;
        this.factory = factory;
        this.etatPaiementService = etatPaiementService;
        this.paiementService = paiementService;
    }


    public VenteGetDto findById(Long id){
        Vente vente = venteRepository.findById(id).orElseThrow(()->new EntityNotFoundException(VENTE, id));
        return venteMapper.toDto(vente);
    }

    public Page<VenteGetDto> findAll(Pageable pageable) {
        return venteRepository.findAll(pageable).map(venteMapper::toDto);
    }

    public Vente save(VentePostDto ventePostDto) {

        Vente vente = venteMapper.toEntity(ventePostDto);
        applyAssociations(vente, ventePostDto.getClientId(), ventePostDto.getEmployeId(), ventePostDto.getEtatId());

        vente.setTenantId(TenantContext.getTenantId());

        Vente save = venteRepository.save(vente);
        return save;
    }

    public Vente updateVente(VenteUpdateDto venteUpdateDto, Long id) {
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VENTE, id));

        venteMapper.updateDto(venteUpdateDto, vente);

        vente.setTenantId(TenantContext.getTenantId());

        Vente save = venteRepository.save(vente);
        return save;
    }


    @Transactional
    public VenteGetDto validerVenteDansPanier(ValiderPanierDTO validerPanierDTO) {

        Client client = clientService.findById(validerPanierDTO.getIdClient());
        EmployeGetDto employe = employeService.findById(validerPanierDTO.getIdEmploye());
        Panier panier = panierService.findById(validerPanierDTO.getIdPanier());
        EtatVente etatVente = etatVenteRepository.findByLibelle("EN_ATTENTE_PAIEMENT").orElseThrow(() -> new EntityNotFoundException("Etat vente", "CREE"));

        List<PanierProduitGetDto> panierProduits = panierProduitService.findAllByPanierId(panier.getId());

        if (panierProduits.isEmpty()) {
            throw new NotFoundException("Panier vide ");
        }
        Double montantTotal = panierProduits.stream()
                .mapToDouble(ligneCaisse -> ligneCaisse.getQuantite() * ligneCaisse.getPrixVente())
                .sum();


        VentePostDto ventePostDto = VentePostDto.builder()
                .clientId(client.getId())
                .montantTotal(montantTotal)
                .employeId(employe.getId())
                .etatId(etatVente.getId())
                .updatedAt(LocalDateTime.now())
                .actif(true)
                .build();
        Vente vente = this.save(ventePostDto);


        panierProduits.forEach(lignePanier -> {

            LigneVentePostDto ligneVentePostDto = LigneVentePostDto.builder()
                    .venteId(vente.getId())
                    .prixVente(lignePanier.getPrixVente())
                    .produitId(lignePanier.getProduit().getId())
                    .quantite(lignePanier.getQuantite())
                    .formatVenteId(lignePanier.getFormatVenteId())
                    .build();
            ligneVenteService.save(ligneVentePostDto);
        });

        //gen facture CREE

        FacturePostDTO facturePostDTO = FacturePostDTO.builder()
                .venteId(vente.getId())
                .numeroFacture(FactureNumeroGenerator.generateNumeroFacture())
                .etatId(etatFactureService.findByLibelle("CREEE").getId())
                .build();

        factureService.createFacture(facturePostDTO);

        return venteMapper.toDto(vente);
    }

    @Transactional
    public void softDeleteVente(Long id) {

        List<LigneVente> ligneVentes = ligneVenteRepository.findByVenteId(id);
        for (LigneVente ligneVente : ligneVentes) {
            ligneVenteService.deleteLinesByIdSoft(ligneVente.getId());
        }

        this.deleteVenteByIdSoft(id);
    }

    public void deleteVenteByIdSoft(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        vente.setActif(false);  // Soft delete
        venteRepository.save(vente);
    }




    @Transactional
    public void payer(Long venteId, BigDecimal montantPaiement) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

        BigDecimal montantTotal = BigDecimal.valueOf(vente.getMontantTotal());
        BigDecimal totalDejaPaye = vente.getPaiements().stream()
                .map(Paiement::getMontantPaye).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalApresPaiement = totalDejaPaye.add(montantPaiement);
        BigDecimal resteAPayer = montantTotal.subtract(totalApresPaiement);

        if (resteAPayer.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(String.format("Montant de paiement invalide: Montant total %s << montant paiement %s",
                    montantTotal, montantPaiement));
        }

        if (resteAPayer.compareTo(BigDecimal.ZERO) == 0) {

            if (totalDejaPaye.compareTo(BigDecimal.ZERO) == 0) {

                fairePaiement(vente, montantPaiement);

            } else if (totalDejaPaye.compareTo(BigDecimal.ZERO) > 0) {

                fairePaiementTotal(vente, montantPaiement);

            }
        } else if (resteAPayer.compareTo(BigDecimal.ZERO) > 0) {

            fairePaiementPartiel(vente, montantPaiement);

        }
    }


    @Transactional
    public void fairePaiement(Vente vente, BigDecimal montantPaiement) {


        // Récupération et synchronisation de la machine d'état
        StateMachine<VenteEnum, VenteEvent> sm = factory.getStateMachine(vente.getId().toString());
        sm.getStateMachineAccessor().doWithAllRegions(access ->
                access.resetStateMachine(new DefaultStateMachineContext<>(
                        VenteEnum.valueOf(vente.getEtat().getLibelle()),
                        null, null, null)));

        // Mise à jour de l'état de la vente
        VenteEvent event = VenteEvent.FAIRE_PAIEMENT;
        if (vente.getEtat().getLibelle().equals("VENTE_A_CREDIT")) {
            event = VenteEvent.FAIRE_PAIEMENT_TOTAL_DETTE;
        }
        boolean ok = sm.sendEvent(event);
        if (!ok) throw new IllegalStateException("Transition non autorisée");
        String etatMachine = sm.getState().getId().toString();

        // Sauvegarde du paiement
        Paiement paiement = Paiement.builder()
                .etat(etatPaiementService.findByLibelle(etatMachine))
                .vente(vente)
                .montantPaye(montantPaiement)
                .updatedAt(LocalDateTime.now())
                .build();
        paiementService.savePaiement(paiement);

        vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                new EntityNotFoundException(ETAT_VENTE, etatMachine)));
        vente.setTenantId(TenantContext.getTenantId());

        venteRepository.save(vente);
    }


    public void marquerCommeCredit(Long venteId) {

        Vente vente = venteRepository.findById(venteId).orElseThrow(() -> new EntityNotFoundException(VENTE, venteId));


        // Récupération et synchronisation de la machine d'état
        StateMachine<VenteEnum, VenteEvent> sm = factory.getStateMachine(vente.getId().toString());
        sm.getStateMachineAccessor().doWithAllRegions(access ->
                access.resetStateMachine(new DefaultStateMachineContext<>(
                        VenteEnum.valueOf(vente.getEtat().getLibelle()),
                        null, null, null)));


        // Mise à jour de l'état de la vente

        boolean ok = sm.sendEvent(VenteEvent.MARQUER_COMME_CREDIT);
        if (!ok) throw new IllegalStateException("Transition non autorisée");
        String etatMachine = sm.getState().getId().toString();

        // Sauvegarde du paiement
        Paiement paiement = Paiement.builder()
                .etat(etatPaiementService.findByLibelle(etatMachine))
                .vente(vente)
                .montantPaye(BigDecimal.valueOf(0))
                .updatedAt(LocalDateTime.now())
                .build();
        paiementService.savePaiement(paiement);

        vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                new EntityNotFoundException(ETAT_VENTE, etatMachine)));
        vente.setTenantId(TenantContext.getTenantId());

        venteRepository.save(vente);

    }


    public void fermerVente(Long venteId) {

        Vente vente = venteRepository.findById(venteId).orElseThrow(() -> new EntityNotFoundException(VENTE, venteId));


        // Récupération et synchronisation de la machine d'état
        StateMachine<VenteEnum, VenteEvent> sm = factory.getStateMachine(vente.getId().toString());
        sm.getStateMachineAccessor().doWithAllRegions(access ->
                access.resetStateMachine(new DefaultStateMachineContext<>(
                        VenteEnum.valueOf(vente.getEtat().getLibelle()),
                        null, null, null)));


        // Mise à jour de l'état de la vente

        boolean ok = sm.sendEvent(VenteEvent.FERMER_VENTE);
        if (!ok) throw new IllegalStateException("Transition non autorisée");
        String etatMachine = sm.getState().getId().toString();

        vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                new EntityNotFoundException(ETAT_VENTE, etatMachine)));

        vente.setTenantId(TenantContext.getTenantId());
        venteRepository.save(vente);

    }

    @Transactional
    public void fairePaiementTotal(Vente vente, BigDecimal montantPaiement) {


        if (montantPaiement.compareTo(BigDecimal.valueOf(vente.getMontantTotal())) < 0) {


            // Récupération et synchronisation de la machine d'état
            StateMachine<VenteEnum, VenteEvent> sm = factory.getStateMachine(vente.getId().toString());
            sm.getStateMachineAccessor().doWithAllRegions(access ->
                    access.resetStateMachine(new DefaultStateMachineContext<>(
                            VenteEnum.valueOf(vente.getEtat().getLibelle()),
                            null, null, null)));


            // Mise à jour de l'état de la vente

            VenteEvent event = VenteEvent.FAIRE_PAIEMENT_TOTAL;
            boolean ok = sm.sendEvent(event);
            if (!ok) throw new IllegalStateException("Transition non autorisée");
            String etatMachine = sm.getState().getId().toString();


            // Sauvegarde du paiement
            Paiement paiement = Paiement.builder()
                    .etat(etatPaiementService.findByLibelle(etatMachine))
                    .vente(vente)
                    .montantPaye(montantPaiement)
                    .updatedAt(LocalDateTime.now())
                    .build();
            paiementService.savePaiement(paiement);

            vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                    new EntityNotFoundException(ETAT_VENTE, etatMachine)));

            vente.setTenantId(TenantContext.getTenantId());
            venteRepository.save(vente);

        }
    }


    @Transactional
    public void fairePaiementPartiel(Vente vente, BigDecimal montantPaiement) {


        if (montantPaiement.compareTo(BigDecimal.valueOf(vente.getMontantTotal())) < 0) {

            // Récupération et synchronisation de la machine d'état
            StateMachine<VenteEnum, VenteEvent> sm = factory.getStateMachine(vente.getId().toString());
            sm.getStateMachineAccessor().doWithAllRegions(access ->
                    access.resetStateMachine(new DefaultStateMachineContext<>(
                            VenteEnum.valueOf(vente.getEtat().getLibelle()),
                            null, null, null)));


            // Mise à jour de l'état de la vente

            VenteEvent event = VenteEvent.FAIRE_PAIEMENT_PARTIEL;
            if (vente.getEtat().getLibelle().equals("VENTE_A_CREDIT")) {
                event = VenteEvent.FAIRE_PAIEMENT_DETTE;
            }
            boolean ok = sm.sendEvent(event);
            if (!ok) throw new IllegalStateException("Transition non autorisée");

            String etatMachine = sm.getState().getId().toString();

            // Sauvegarde du paiement
            Paiement paiement = Paiement.builder()
                    .etat(etatPaiementService.findByLibelle(etatMachine))
                    .vente(vente)
                    .montantPaye(montantPaiement)
                    .updatedAt(LocalDateTime.now())
                    .build();
            paiementService.savePaiement(paiement);

            vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                    new EntityNotFoundException(ETAT_VENTE, etatMachine)));
            vente.setTenantId(TenantContext.getTenantId());
            venteRepository.save(vente);

        }


    }


    private void applyAssociations(Vente vente, Long clientId, Long employeId, Long etatId) {
        if (clientId != null) {
            vente.setClient(
                    clientRepository.findById(clientId)
                            .orElseThrow(() -> new EntityNotFoundException("Client", clientId))
            );
        }
        if (employeId != null) {
            vente.setEmploye(
                    employeRepository.findById(employeId)
                            .orElseThrow(() -> new EntityNotFoundException("Employe", employeId))
            );
        }
        if (etatId != null) {
            vente.setEtat(
                    etatVenteRepository.findById(etatId)
                            .orElseThrow(() -> new EntityNotFoundException("EtatVente", etatId))
            );
        }
    }
}
