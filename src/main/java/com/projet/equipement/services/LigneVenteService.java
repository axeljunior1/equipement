package com.projet.equipement.services;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.exceptions.StockInsuffisantException;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.FormatVenteRepository;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.VenteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LigneVenteService {

    private final LigneVenteRepository ligneVenteRepository;
    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;
    private final LigneVenteMapper ligneVenteMapper;
    private final ProduitRepository produitRepository;
    private final FormatVenteRepository formatVenteRepository;
    private final FormatVenteService formatVenteService;
    private final MouvementStockService mouvementStockService;
    private final StockCourantService stockCourantService;

    public LigneVenteService(LigneVenteRepository ligneVenteRepository, VenteRepository venteRepository, VenteMapper venteMapper, LigneVenteMapper ligneVenteMapper, ProduitRepository produitRepository, FormatVenteRepository formatVenteRepository, FormatVenteService formatVenteService, MouvementStockService mouvementStockService, StockCourantService stockCourantService) {
        this.ligneVenteRepository = ligneVenteRepository;
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
        this.ligneVenteMapper = ligneVenteMapper;
        this.produitRepository = produitRepository;
        this.formatVenteRepository = formatVenteRepository;
        this.formatVenteService = formatVenteService;
        this.mouvementStockService = mouvementStockService;
        this.stockCourantService = stockCourantService;
    }

    public LigneVenteGetDto findById(Long id){
        LigneVente ligneVente = ligneVenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ligne vente", id));
        return ligneVenteMapper.toDto(ligneVente);
    }



    public Page<LigneVenteGetDto> findAll(Pageable pageable) {
        Page<LigneVente> allLine = ligneVenteRepository.findAllLine(pageable);
        return allLine.map(ligneVenteMapper::toDto);
    }



    @Transactional
    public LigneVente save(LigneVentePostDto ligneVentePostDto) {
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

        ligneVente.setVente(venteRepository.findById(ligneVentePostDto.getVenteId()).orElseThrow(() -> new EntityNotFoundException("Vente", ligneVentePostDto.getVenteId())));
        ligneVente.setProduit(produitRepository.findById(ligneVentePostDto.getProduitId()).orElseThrow(() -> new EntityNotFoundException("Produit", ligneVentePostDto.getProduitId())));
        ligneVente.setFormatVente(formatVenteRepository.findById(ligneVentePostDto.getFormatVenteId()).orElseThrow(() -> new EntityNotFoundException("Format de vente", ligneVentePostDto.getFormatVenteId())));

        FormatVente formatVente = formatVenteService.findById(ligneVentePostDto.getFormatVenteId());
        ligneVente.setFormatVente(formatVente);

        ligneVente.setTenantId(TenantContext.getTenantId());
        LigneVente saveLigneVente = ligneVenteRepository.save(ligneVente);


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
                .idEvenementOrigine(saveLigneVente.getVente().getId())
                .idLigneOrigine(saveLigneVente.getId())
                .build();
        mouvementStockService.save(mouvStk);


        return saveLigneVente;
    }

    @Transactional
    public void updateTotalVente(Long venteId) {
        Vente vente = venteRepository.findById(venteId)
                .orElseThrow(() -> new EntityNotFoundException("Vente", venteId));

        Double total = ligneVenteRepository.sumTotalByVenteId(venteId);
        vente.setMontantTotal(total != null ? total : 0.0);

        vente.setTenantId(TenantContext.getTenantId());
        venteRepository.save(vente);
    }


    @Transactional
    public void deleteLinesByIdSoft(Long id) {
        // cet id est l'id de la ligne d'achat
        LigneVente ligneVente = ligneVenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ligne vente", id));

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
                .idEvenementOrigine(ligneVente.getVente().getId())
                .idLigneOrigine(ligneVente.getId())
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneVente.setActif(false);
        ligneVenteRepository.save(ligneVente);

        updateTotalVente(ligneVente.getVente().getId());
    }


    public Page<LigneVenteGetDto> findByVenteId(Long id, Pageable pageable) {
        Page<LigneVente> byVenteId = ligneVenteRepository.findByVenteId(id, pageable);
        return byVenteId.map(ligneVenteMapper::toDto);
    }


    public LigneVente updateLigneVente(LigneVenteUpdateDto ligneVenteUpdateDto, Long id) {
        LigneVente ligneVente = ligneVenteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ligne vente", id));

        ligneVenteMapper.updateLigneVenteFromDto(ligneVenteUpdateDto, ligneVente);

        ligneVente.setVente(venteRepository.findById(ligneVenteUpdateDto.getVenteId()).orElseThrow(() -> new EntityNotFoundException("Vente", ligneVenteUpdateDto.getVenteId())));
        ligneVente.setProduit(produitRepository.findById(ligneVenteUpdateDto.getProduitId()).orElseThrow(() -> new EntityNotFoundException("Produit", ligneVenteUpdateDto.getProduitId())));
        ligneVente.setFormatVente(formatVenteRepository.findById(ligneVenteUpdateDto.getFormatVenteId()).orElseThrow(() -> new EntityNotFoundException("Format de vente", ligneVenteUpdateDto.getFormatVenteId())));

        ligneVente.setTenantId(TenantContext.getTenantId());

        return ligneVenteRepository.save(ligneVente);
    }
}
