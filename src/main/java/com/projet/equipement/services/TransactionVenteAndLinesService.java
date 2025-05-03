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
import com.projet.equipement.repository.*;
import com.projet.equipement.utils.FactureNumeroGenerator;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Qualifier("venteStateMachineConfig")
    private final StateMachine<VenteEnum, VenteEvent> stateMachine;
    private final EtatVenteRepository etatVenteRepository;
    private final EtatFactureService etatFactureService;
    private final FactureService factureService;
    private final EtatVenteService etatVenteService;
    private final PaiementRepository paiementRepository;
    private final EtatPaiementRepository etatPaiementRepository;

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
            PanierService panierService,
            StateMachine<VenteEnum, VenteEvent> stateMachine,
            EtatVenteRepository etatVenteRepository,
            EtatFactureService etatFactureService,
            FactureService factureService,
            EtatVenteService etatVenteService, PaiementRepository paiementRepository, EtatPaiementRepository etatPaiementRepository) {
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
        this.stateMachine = stateMachine;
        this.etatVenteRepository = etatVenteRepository;
        this.etatFactureService = etatFactureService;
        this.factureService = factureService;
        this.etatVenteService = etatVenteService;
        this.paiementRepository = paiementRepository;
        this.etatPaiementRepository = etatPaiementRepository;
    }

    private static final String VENTE = "Vente";
    private static final String ETAT_VENTE = "EtatVente";
    private static final Logger logger = LoggerFactory.getLogger(VenteService.class);


//VALIDER_PANIER
//    FAIRE_PAIEMENT
//    MARQUER_COMME_CREDIT
//    FERMER_VENTE
//    ANNULER_VENTE

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

            } else if (totalDejaPaye.compareTo(BigDecimal.ZERO)>0) {

                fairePaiementTotal(vente, montantPaiement);

            }
        } else if (resteAPayer.compareTo(BigDecimal.ZERO) > 0) {

            fairePaiementPartiel(vente, montantPaiement);

        }
    }


    @Transactional
    public void fairePaiement(Vente vente, BigDecimal montantPaiement) {


        // Sauvegarde du paiement
        Paiements paiement = new Paiements();
        paiement.setVente(vente);
        paiement.setMontantPaye(montantPaiement);
        paiement.setUpdatedAt(LocalDateTime.now());
        paiement.setEtat(etatPaiementRepository.findByLibelle("PAYEE").orElseThrow(() -> new EntityNotFoundException(ETAT_VENTE, "PAYEE")));
        paiementRepository.save(paiement);

        // Récupération et synchronisation de la machine d'état
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(
                        new DefaultStateMachineContext<>(VenteEnum.valueOf(vente.getEtat().getLibelle()), null, null, null)));
        stateMachine.start();


        boolean ok = stateMachine.sendEvent(VenteEvent.FAIRE_PAIEMENT);
        if (!ok) throw new IllegalStateException("Transition non autorisée");

        // Mise à jour de l'état de la vente
        String etatMachine = stateMachine.getState().getId().toString();
        stateMachine.stop();

        vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                new EntityNotFoundException(ETAT_VENTE, etatMachine)));

        venteRepository.save(vente);
    }


    public void marquerCommeCredit(Long venteId) {

    }


    public void annulerVente(Long venteId) {

    }


    public void fermerVente(Long venteId) {

        Vente vente = venteRepository.findById(venteId).orElseThrow(() -> new EntityNotFoundException(VENTE, venteId));

            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access -> access.resetStateMachine(
                            new DefaultStateMachineContext<>(VenteEnum.valueOf(vente.getEtat().getLibelle()), null, null, null)));
            stateMachine.start();


            stateMachine.sendEvent(VenteEvent.FERMER_VENTE);


            String etatMachine = stateMachine.getState().getId().toString();
            vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                    new EntityNotFoundException(ETAT_VENTE, etatMachine)));
            stateMachine.stop();

            venteRepository.save(vente);

    }

    @Transactional
    public void fairePaiementTotal(Vente vente, BigDecimal montantPaiement) {


        if (montantPaiement.compareTo(BigDecimal.valueOf(vente.getMontantTotal())) < 0) {


            // Sauvegarde du paiement
            Paiements paiement = new Paiements();
            paiement.setVente(vente);
            paiement.setMontantPaye(montantPaiement);
            paiement.setUpdatedAt(LocalDateTime.now());
            paiement.setEtat(etatPaiementRepository.findByLibelle("PAYEE").orElseThrow(() -> new EntityNotFoundException(ETAT_VENTE, "PAYEE")));
            paiementRepository.save(paiement);

            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access -> access.resetStateMachine(
                            new DefaultStateMachineContext<>(VenteEnum.valueOf(vente.getEtat().getLibelle()), null, null, null)));
            stateMachine.start();


            stateMachine.sendEvent(VenteEvent.FAIRE_PAIEMENT_TOTAL);


            String etatMachine = stateMachine.getState().getId().toString();
            vente.setEtat(etatVenteRepository.findByLibelle(etatMachine).orElseThrow(() ->
                    new EntityNotFoundException(ETAT_VENTE, etatMachine)));
            stateMachine.stop();

            venteRepository.save(vente);

        }
    }


    @Transactional
    public void fairePaiementPartiel(Vente vente, BigDecimal montantPaiement) {


        if (montantPaiement.compareTo(BigDecimal.valueOf(vente.getMontantTotal())) < 0) {

            // Sauvegarde du paiement
            Paiements paiement = new Paiements();
            paiement.setVente(vente);
            paiement.setMontantPaye(montantPaiement);
            paiement.setUpdatedAt(LocalDateTime.now());
            paiement.setEtat(etatPaiementRepository.findByLibelle("PAYEE").orElseThrow(() -> new EntityNotFoundException("EtatPaiement", "PAYEE")));
            paiementRepository.save(paiement);

            stateMachine.stop();
            stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access -> access.resetStateMachine(
                            new DefaultStateMachineContext<>(VenteEnum.valueOf(vente.getEtat().getLibelle()), null, null, null)));
            stateMachine.start();

            stateMachine.sendEvent(VenteEvent.FAIRE_PAIEMENT_PARTIEL);


            vente.setEtat(etatVenteRepository.findByLibelle(stateMachine.getState().getId().toString()).orElseThrow(() ->
                    new EntityNotFoundException(ETAT_VENTE, stateMachine.getState().getId().toString())));
            stateMachine.stop();
            venteRepository.save(vente);

        }


    }


    @Transactional
    public void updateTotalVente(Long venteId) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new EntityNotFoundException(VENTE, venteId));

        Double total = ligneVenteRepository.sumTotalByVenteId(venteId);
        vente.setMontantTotal(total != null ? total : 0.0);

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

        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVenteToPost);

        entityManager.refresh(saveLigneVente);

        updateTotalVente(saveLigneVente.getVente().getId());


        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.save(MouvementStockPostDto.builder()
                .reference("VTE_" + ligneVentePostDto.getVenteId() + "_LIG_" + saveLigneVente.getId())
                .produitId(ligneVentePostDto.getProduitId())
                .quantite(ligneVentePostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un vente")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("VENTE_PRODUIT")
                .idEvenementOrigine(Math.toIntExact(saveLigneVente.getVente().getId()))
                .idLigneOrigine(Math.toIntExact(saveLigneVente.getId()))
                .build());


        return saveLigneVente;
    }

    public Vente saveVente(VentePostDto ventePostDto) {

        Vente vente = venteMapper.toEntity(ventePostDto);

        Vente save = venteRepository.save(vente);
        entityManager.refresh(save);
        return save;
    }

    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id) {
        LigneVente ligneVente = this.findByIdLine(id);

        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente);
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
        ligneVenteRepository.save(ligneVente);
        updateTotalVente(ligneVente.getVente().getId());
    }


}
