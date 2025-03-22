package com.projet.equipement.services;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.facture.FactureGetDTO;
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
import com.projet.equipement.enumeration.VenteTransitionEnum;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.exceptions.StockInsuffisantException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.*;
import com.projet.equipement.utils.FactureNumeroGenerator;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final EmployeService employeService;
    private final PanierService panierService;
    private final StateMachine<VenteEnum, String> stateMachine;
    private final EtatVenteRepository etatVenteRepository;
    private final EtatFactureRepository etatFactureRepository;
    private final EtatFactureService etatFactureService;
    private final FactureService factureService;
    private final PaiementService paiementService;
    private final EtatPaiementService etatPaiementService;
    private final EtatVenteService etatVenteService;

    public TransactionVenteAndLinesService(
            LigneVenteMapper ligneVenteMapper,
            LigneVenteRepository ligneVenteRepository,
            MouvementStockService mouvementStockService,
            VenteMapper venteMapper,
            VenteRepository venteRepository,
            StockCourantService stockCourantService,
            EntityManager entityManagerFactory,
            PanierProduitService panierProduitService,
            ClientRepository clientRepository,
            ClientService clientService,
            EmployeService employeService,
            PanierService panierService,
            StateMachine<VenteEnum, String> stateMachine,
            EtatVenteRepository etatVenteRepository,
            EtatFactureRepository etatFactureRepository,
            EtatFactureService etatFactureService,
            FactureService factureService,
            PaiementService paiementService,
            EtatPaiementService etatPaiementService,
            EtatVenteService etatVenteService) {
        this.ligneVenteMapper = ligneVenteMapper;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementStockService = mouvementStockService;
        this.venteMapper = venteMapper;
        this.venteRepository = venteRepository;
        this.stockCourantService = stockCourantService;
        this.entityManager = entityManagerFactory;
        this.panierProduitService = panierProduitService;
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.employeService = employeService;
        this.panierService = panierService;
        this.stateMachine = stateMachine;
        this.etatVenteRepository = etatVenteRepository;
        this.etatFactureRepository = etatFactureRepository;
        this.etatFactureService = etatFactureService;
        this.factureService = factureService;
        this.paiementService = paiementService;
        this.etatPaiementService = etatPaiementService;
        this.etatVenteService = etatVenteService;
    }


    @Transactional
    public void updateTotalVente(Long venteId) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new EntityNotFoundException("Vente", venteId));

        Double total = ligneVenteRepository.sumTotalByVenteId(venteId);
        vente.setMontantTotal(total != null ? total : 0.0);

        venteRepository.save(vente);
    }


    @Transactional
    public VenteGetDto validerVenteDansPanier(ValiderPanierDTO validerPanierDTO) {
        Client client = clientService.findById(validerPanierDTO.getIdClient());
        EmployeGetDto employe = employeService.findById(validerPanierDTO.getIdEmploye());
        Panier panier = panierService.findById(validerPanierDTO.getIdPanier());
        EtatVente etatVente = etatVenteRepository.findByLibelle("CREEE").orElseThrow(()->new EntityNotFoundException("EtatVente", "CREE" ));

        List<PanierProduitGetDto> panierProduits = panierProduitService.findAllByPanierId(panier.getId());

        if (panierProduits.isEmpty()) {
            throw new NotFoundException("Panier vide ");
        }
//        Double mTotal = 0D ;
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
            this.saveLigneVente(ligneVentePostDto);
        });

        //gen facture CREE

        FacturePostDTO facturePostDTO = FacturePostDTO.builder()
                .venteId(vente.getId())
                .numeroFacture(FactureNumeroGenerator.generateNumeroFacture())
                .etatId(etatFactureService.findByLibelle("CREEE").getId())
                .build();

        FactureGetDTO facture = factureService.createFacture(facturePostDTO);

        //gen paiement etat => EN_ATTENTE

//        PaiementPostDTO  paiementPostDTO = PaiementPostDTO.builder()
//                .factureId(facture.getIdFacture())
//                .modePaiement("")
//                .montantPaye(BigDecimal.valueOf(0.000))
//                .etatId(etatPaiementService.findByLibelle("EN_ATTENTE").getId())
//                .build();
//
//        paiementService.createPaiement(paiementPostDTO);

        return venteMapper.toDto(vente);
    }

    public VenteGetDto payerVente( Long id){
        Vente vente = venteRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Vente", id));

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
                .orElseThrow(() -> new EntityNotFoundException("Vente", id)));
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
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

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

    // State Machine

    public void payer(Long id) {

        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));

        // Démarrer le processus avec Spring State Machine
        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(VenteTransitionEnum.CONFIRMEE_PAYEE));

        System.out.println(stateMachine.getState());

        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatVente etatVente = etatVenteRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatVente", libelle));

        vente.setEtat(etatVente);
        venteRepository.save(vente);
    }

    public void annuler(Long id) {
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));


        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(VenteTransitionEnum.CONFIRMEE_ANNULEE));


        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatVente etatVente = etatVenteRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatVente", libelle));

        vente.setEtat(etatVente);
        venteRepository.save(vente);
    }


    public void rembourser(Long id) {
        Vente vente = venteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Vente", id));


        stateMachine.start();
        stateMachine.sendEvent(String.valueOf(VenteTransitionEnum.PAYEE_REMBOURSEE));


        // Mettre à jour l'état de la vente dans la base de données
        String libelle = String.valueOf(stateMachine.getState().getId());
        EtatVente etatVente = etatVenteRepository.findByLibelle(libelle)
                .orElseThrow(() -> new EntityNotFoundException("EtatVente", libelle));

        vente.setEtat(etatVente);
        venteRepository.save(vente);
    }

}
