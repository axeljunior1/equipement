package com.projet.equipement.services;

import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.exceptions.StockInsuffisantException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.VenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionVenteAndLinesService {

    private final ProduitService produitService;
    private final LigneVenteMapper ligneVenteMapper;
    private final LigneVenteRepository ligneVenteRepository;
    private final MouvementStockService mouvementStockService;
    private final VenteMapper venteMapper;
    private final VenteRepository venteRepository;
    private final EmployeService employeService;
    private final ClientService clientService;
    private final StockCourantService stockCourantService;

    public TransactionVenteAndLinesService(ProduitService produitService, LigneVenteMapper ligneVenteMapper, LigneVenteRepository ligneVenteRepository, MouvementStockService mouvementStockService, VenteMapper venteMapper, VenteRepository venteRepository, EmployeService employeService, ClientService clientService, StockCourantService stockCourantService) {
        this.produitService = produitService;
        this.ligneVenteMapper = ligneVenteMapper;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementStockService = mouvementStockService;
        this.venteMapper = venteMapper;
        this.venteRepository = venteRepository;
        this.employeService = employeService;
        this.clientService = clientService;
        this.stockCourantService = stockCourantService;
    }


    public Page<LigneVente> findByVenteId(Long id, Pageable pageable) {
        return ligneVenteRepository.findByVenteId(id, pageable);
    }

    public List<LigneVente> findByVenteId(Long id) {
        return ligneVenteRepository.findByVenteId(id);
    }

    public Vente findByIdVente(Long id) {
        return venteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vente", id));
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
    public void softDeleteVente(Long id){

        this.SoftdeleteLineById(id);

        this.deleteVenteByIdSoft(id);
    }

    public Page<Vente> findAllVentes(Pageable pageable) {
        return venteRepository.findAll(pageable);
    }


    @Transactional
    public LigneVente saveLigneVente(LigneVentePostDto ligneVentePostDto) {
        Vente vente = null;
        if(ligneVentePostDto.getVenteId() != null) {
            vente = this.findByIdVente(Long.valueOf(ligneVentePostDto.getVenteId()));
        }
        Produit produit = null;
        if(ligneVentePostDto.getProduitId() != null) {
            produit = produitService.findById(Long.valueOf(ligneVentePostDto.getProduitId()));
        }

        LigneVente ligneVenteToPost = ligneVenteMapper.postLigneVenteFromDto(ligneVentePostDto, vente, produit);

        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVenteToPost);

        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.save(MouvementStockPostDto.builder()
                .reference( "ACH_" + ligneVentePostDto.getVenteId() + "_LIG_" + saveLigneVente.getId())
                .produitId(Long.valueOf(ligneVentePostDto.getProduitId()))
                .quantite(ligneVentePostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un vente")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("ACHAT_MARCHANDISE")
                .idEvenementOrigine(Math.toIntExact(saveLigneVente.getVente().getId()))
                .idLigneOrigine(Math.toIntExact(saveLigneVente.getId()))
                .build());


        return saveLigneVente;
    }

    public Vente saveVente(VentePostDto ventePostDto) {
//        Set<Role> roles = vente.getRoles();
        Employe employe = null;
        if (ventePostDto.getEmployeId() != null) {
            employe = employeService.findById(ventePostDto.getEmployeId());
        }
        Client client = null;
        if (ventePostDto.getClientId() != null) {
            client = clientService.findById(ventePostDto.getClientId());
        }
        Vente vente =  venteMapper.postVenteDto(ventePostDto, client, employe);

        return venteRepository.save(vente);
    }

    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id) {
        LigneVente ligneVente = this.findByIdLine(id);

        Vente vente = null;
        if(ligneVenteUpdateDto.getVenteId() != null) {
            vente = this.findByIdVente(Long.valueOf(ligneVenteUpdateDto.getVenteId())) ;
        }
        Produit produit = null;
        if(ligneVenteUpdateDto.getProduitId() != null) {
            produit = produitService.findById(Long.valueOf(ligneVenteUpdateDto.getProduitId())) ;
        }
        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente, vente, produit);
        return ligneVenteRepository.save(ligneVente);
    }


    /**
     * Pour la mise à jour d'un vente
     * @param venteUpdateDto
     * @param id
     * @return
     */
    public Vente updateVente(VenteUpdateDto venteUpdateDto, Long id) {
        Vente vente = this.findByIdVente(id);
        Employe employe = null;
        if (venteUpdateDto.getEmployeId() != null) {
            employe = employeService.findById(Long.valueOf(venteUpdateDto.getEmployeId()));
        }
        Client client = null;
        if (venteUpdateDto.getClientId() != null) {
            client = clientService.findById(Long.valueOf(venteUpdateDto.getClientId()));
        }
        venteMapper.updateVenteFromDto(venteUpdateDto,vente, client , employe);
        return venteRepository.save(vente);
    }



    public Page<LigneVente> findAllLine(Pageable pageable) {
        return ligneVenteRepository.findAll(pageable);
    }

    public LigneVente findByIdLine(Long id) {
        return ligneVenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneVente", id));
    }

    @Transactional
    public LigneVente saveLine(LigneVentePostDto ligneVentePostDto) {
        // Récupération du produit ID
        Long produitId = Long.valueOf(ligneVentePostDto.getProduitId());

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


        Vente vente = null;
        if (ligneVentePostDto.getVenteId() != null) {
            vente = this.findByIdVente(Long.valueOf(ligneVentePostDto.getVenteId()));
        }

        Produit produit = null;
        if (ligneVentePostDto.getProduitId() != null) {
            produit = produitService.findById(Long.valueOf(ligneVentePostDto.getProduitId()));
        }
        LigneVente ligneVente = ligneVenteMapper.postLigneVenteFromDto(ligneVentePostDto, vente, produit);
        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVente);
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
    public void SoftdeleteLineById(Long id) {
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
                .typeMouvementCode("RETOUR_FOURNISSEUR")
                .idEvenementOrigine(Math.toIntExact(ligneVente.getVente().getId()))
                .idLigneOrigine(Math.toIntExact(ligneVente.getId()))
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneVente.setActif(false);
        ligneVenteRepository.save(ligneVente);
    }


}
