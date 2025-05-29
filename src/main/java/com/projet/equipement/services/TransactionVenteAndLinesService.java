package com.projet.equipement.services;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.facture.FacturePostDTO;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.validerPanier.ValiderPanierDTO;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.enumeration.VenteEnum;
import com.projet.equipement.enumeration.VenteEvent;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.exceptions.StockInsuffisantException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.EtatVenteRepository;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.VenteRepository;
import com.projet.equipement.utils.FactureNumeroGenerator;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransactionVenteAndLinesService {

    private final LigneVenteMapper ligneVenteMapper;
    private final LigneVenteRepository ligneVenteRepository;
    private final MouvementStockService mouvementStockService;
    private final VenteMapper venteMapper;
    private final VenteRepository venteRepository;
    private final StockCourantService stockCourantService;
    private final EntityManager entityManager;
    private final PanierProduitService panierProduitService;
    private final ClientService clientService;
    private final EmployeService employeService;
    private final PanierService panierService;
    private final StateMachineFactory<VenteEnum, VenteEvent> factory;
    private final EtatVenteRepository etatVenteRepository;
    private final EtatFactureService etatFactureService;
    private final FactureService factureService;
    private final EtatVenteService etatVenteService;
    private final PaiementService paiementService;
    private final EtatPaiementService etatPaiementService;
    private final FormatVenteService formatVenteService;

    @Autowired
    public TransactionVenteAndLinesService(
            LigneVenteMapper ligneVenteMapper,
            LigneVenteRepository ligneVenteRepository,
            MouvementStockService mouvementStockService,
            VenteMapper venteMapper,
            VenteRepository venteRepository,
            StockCourantService stockCourantService,
            EntityManager entityManagerFactory,
            PanierProduitService panierProduitService,
            ClientService clientService,
            EmployeService employeService,
            PanierService panierService, StateMachineFactory<VenteEnum, VenteEvent> factory,
            EtatVenteRepository etatVenteRepository,
            EtatFactureService etatFactureService,
            FactureService factureService,
            EtatVenteService etatVenteService, PaiementService paiementService, EtatPaiementService etatPaiementService, FormatVenteService formatVenteService) {
        this.ligneVenteMapper = ligneVenteMapper;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementStockService = mouvementStockService;
        this.venteMapper = venteMapper;
        this.venteRepository = venteRepository;
        this.stockCourantService = stockCourantService;
        this.entityManager = entityManagerFactory;
        this.panierProduitService = panierProduitService;
        this.clientService = clientService;
        this.employeService = employeService;
        this.panierService = panierService;
        this.factory = factory;
        this.etatVenteRepository = etatVenteRepository;
        this.etatFactureService = etatFactureService;
        this.factureService = factureService;
        this.etatVenteService = etatVenteService;
        this.paiementService = paiementService;
        this.etatPaiementService = etatPaiementService;
        this.formatVenteService = formatVenteService;
    }

    private static final String VENTE = "Vente";
    private static final String ETAT_VENTE = "EtatVente";
    private static final Logger logger = LoggerFactory.getLogger(TransactionVenteAndLinesService.class);


    @Transactional
    public void payer(Long venteId, BigDecimal montantPaiement) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new RuntimeException("Vente non trouvée"));

        BigDecimal montantTotal = BigDecimal.valueOf(vente.getMontantTotal());
        BigDecimal totalDejaPaye = vente.getPaiements().stream()
                .map(Paiements::getMontantPaye).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalApresPaiement = totalDejaPaye.add(montantPaiement);
        BigDecimal resteAPayer = montantTotal.subtract(totalApresPaiement);

        if (resteAPayer.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(String.format("Montant de paiement invalide: Montant total %s << montant paiement %s", montantTotal, montantPaiement));
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
        Paiements paiement = Paiements.builder()
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
        Paiements paiement = Paiements.builder()
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


    public void annulerVente(Long venteId) {

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
            Paiements paiement = Paiements.builder()
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
            Paiements paiement = Paiements.builder()
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
    public void updateTotalVente(Long venteId) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new EntityNotFoundException(VENTE, venteId));

        Double total = ligneVenteRepository.sumTotalByVenteId(venteId);
        vente.setMontantTotal(total != null ? total : 0.0);

        vente.setTenantId(TenantContext.getTenantId());
        venteRepository.save(vente);
    }


    @Transactional
    public VenteGetDto validerVenteDansPanier(ValiderPanierDTO validerPanierDTO) {
        Client client = clientService.findById(validerPanierDTO.getIdClient());
        EmployeGetDto employe = employeService.findById(validerPanierDTO.getIdEmploye());
        Panier panier = panierService.findById(validerPanierDTO.getIdPanier());
        EtatVente etatVente = etatVenteRepository.findByLibelle("EN_ATTENTE_PAIEMENT").orElseThrow(() -> new EntityNotFoundException(ETAT_VENTE, "CREE"));

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
        Vente vente = this.saveVente(ventePostDto);


        panierProduits.forEach(lignePanier -> {

            LigneVentePostDto ligneVentePostDto = LigneVentePostDto.builder()
                    .venteId(vente.getId())
                    .prixVente(lignePanier.getPrixVente())
                    .produitId(lignePanier.getProduit().getId())
                    .quantite(lignePanier.getQuantite())
                    .formatVenteId(lignePanier.getFormatVenteId())
                    .build();
            saveLigneVente(ligneVentePostDto);
        });

        //gen facture CREE

        FacturePostDTO facturePostDTO = FacturePostDTO.builder()
                .venteId(vente.getId())
                .numeroFacture(FactureNumeroGenerator.generateNumeroFacture())
                .etatId(etatFactureService.findByLibelle("CREEE").getId())
                .build();

        factureService.createFacture(facturePostDTO);

        //gen paiement etat => EN_ATTENTE

      /*  PaiementPostDTO  paiementPostDTO = PaiementPostDTO.builder()
                .factureId(facture.getIdFacture())
                .modePaiement("")
                .montantPaye(BigDecimal.valueOf(0.000))
                .etatId(etatPaiementService.findByLibelle("EN_ATTENTE").getId())
                .build();

        paiementService.createPaiement(paiementPostDTO);*/

        return venteMapper.toDto(vente);
    }

    public VenteGetDto payerVente(Long id) {
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VENTE, id));

        vente.setEtat(etatVenteService.findByLibelle("CONFIRME"));

        this.saveVente(vente);

        //modif etat fac

        return venteMapper.toDto(vente);
    }


    public Page<LigneVenteGetDto> findByVenteId(Long id, Pageable pageable) {
        Page<LigneVente> byVenteId = ligneVenteRepository.findByVenteId(id, pageable);
        return byVenteId.map(ligneVenteMapper::toDto);
    }

    public List<LigneVente> findByVenteId(Long id) {
        return ligneVenteRepository.findByVenteId(id);
    }

    public VenteGetDto findByIdVente(Long id) {
        return venteMapper.toDto(venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VENTE, id)));
    }

    // Suppression de l'vente
    public void deleteVenteByIdSoft(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        vente.setActif(false);  // Soft delete
        this.saveVente(vente);
    }

    private void saveVente(Vente vente) {
        vente.setTenantId(TenantContext.getTenantId());
        venteRepository.save(vente);
    }


    @Transactional
    public void softDeleteVente(Long id) {

        List<LigneVente> ligneVentes = this.findByVenteId(id);
        for (LigneVente ligneVente : ligneVentes) {
            this.deleteLinesByIdSoft(ligneVente.getId());
        }

        this.deleteVenteByIdSoft(id);
    }

    public Page<VenteGetDto> findAllVentes(Pageable pageable) {
        Page<Vente> allActif = venteRepository.findAllActif(pageable);
        return allActif.map(venteMapper::toDto);
    }


    @Transactional
    public LigneVente saveLigneVente(LigneVentePostDto ligneVentePostDto) {

        LigneVente ligneVenteToPost = ligneVenteMapper.toEntity(ligneVentePostDto);

        FormatVente formatVente = formatVenteService.findById(ligneVentePostDto.getFormatVenteId());
        ligneVenteToPost.setFormatVente(formatVente);

        ligneVenteToPost.setTenantId(TenantContext.getTenantId());
        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVenteToPost);

        entityManager.refresh(saveLigneVente);

        updateTotalVente(saveLigneVente.getVente().getId());


        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        MouvementStockPostDto mouvStk = MouvementStockPostDto.builder()
                .reference("VTE_" + ligneVentePostDto.getVenteId() + "_LIG_" + saveLigneVente.getId())
                .produitId(ligneVentePostDto.getProduitId())
                .quantite(ligneVentePostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un vente")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("VENTE_PRODUIT")
                .idEvenementOrigine(Math.toIntExact(saveLigneVente.getVente().getId()))
                .idLigneOrigine(Math.toIntExact(saveLigneVente.getId()))
                .build();
        mouvementStockService.save(mouvStk);


        return saveLigneVente;
    }

    public Vente saveVente(VentePostDto ventePostDto) {

        Vente vente = venteMapper.toEntity(ventePostDto);

        vente.setTenantId(TenantContext.getTenantId());
        Vente save = venteRepository.save(vente);
        entityManager.refresh(save);
        return save;
    }

    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id) {
        LigneVente ligneVente = this.findByIdLine(id);

        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente);
        ligneVente.setTenantId(TenantContext.getTenantId());
        LigneVente save = ligneVenteRepository.save(ligneVente);

        entityManager.refresh(save);
        return save;
    }


    /**
     * Pour la mise à jour d'un vente
     */
    public Vente updateVente(VenteUpdateDto venteUpdateDto, Long id) {
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VENTE, id));

        venteMapper.updateDto(venteUpdateDto, vente);


        vente.setTenantId(TenantContext.getTenantId());
        Vente save = venteRepository.save(vente);
        entityManager.refresh(save);
        return save;
    }


    public Page<LigneVenteGetDto> findAllLine(Pageable pageable) {
        Page<LigneVente> allLine = ligneVenteRepository.findAllLine(pageable);
        return allLine.map(ligneVenteMapper::toDto);
    }

    public LigneVente findByIdLine(Long id) {
        return ligneVenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneVente", id));
    }

    @Transactional
    public LigneVente saveLine(LigneVentePostDto ligneVentePostDto) {
        // Récupération du produit ID
        Long produitId = ligneVentePostDto.getProduitId();

        // Vérification de la quantité demandée
        Integer qte = ligneVentePostDto.getQuantite();
        if (qte == null || qte <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à zéro.");
        }

        // Vérification du stock courant
        StockCourant stockCourant = stockCourantService.getStockCourantById(produitId);
        if (stockCourant == null) {
            throw new RuntimeException("Stock introuvable pour le produit ID: " + produitId);
        }

        if (stockCourant.getStockCourant() < qte) {
            throw new StockInsuffisantException("Quantité demandée (" + qte + ") supérieure au stock disponible (" + stockCourant.getStockCourant() + ")");
        }


        LigneVente ligneVente = ligneVenteMapper.toEntity(ligneVentePostDto);

        ligneVente.setTenantId(TenantContext.getTenantId());
        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVente);

        entityManager.refresh(saveLigneVente);
        // Gestion du mvt de stock
        mouvementStockService.enregistrerMouvementStock(
                produitId,
                ligneVentePostDto.getQuantite(),
                "VTE_" + ligneVentePostDto.getVenteId() + "_LIG_" + saveLigneVente.getId(),
                "Généré à partir de la ligne d'une vente",
                "VENTE_PRODUIT",
                Math.toIntExact(ligneVente.getVente().getId()),
                Math.toIntExact(ligneVente.getId())
        );
        return saveLigneVente;
    }


    @Transactional
    public void deleteLinesByIdSoft(Long id) {
        // cet id est l'id de la ligne d'achat
        LigneVente ligneVente = this.findByIdLine(id);
        String reference = "ACH_" + ligneVente.getVente().getId() + "_LIG_" + ligneVente.getId() + "_DEL";


        LocalDateTime dateCreate = LocalDateTime.now();

        MouvementStockPostDto mvtInverse = MouvementStockPostDto.builder()
                .reference(reference)
                .produitId(ligneVente.getProduit().getId())
                .quantite(ligneVente.getQuantite())
                .commentaire("Eve inverse pour ajuster le stock ")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("RETOUR_CLIENT")
                .idEvenementOrigine(Math.toIntExact(ligneVente.getVente().getId()))
                .idLigneOrigine(Math.toIntExact(ligneVente.getId()))
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneVente.setActif(false);
        ligneVente.setTenantId(TenantContext.getTenantId());
        ligneVenteRepository.save(ligneVente);
        updateTotalVente(ligneVente.getVente().getId());
    }


}
