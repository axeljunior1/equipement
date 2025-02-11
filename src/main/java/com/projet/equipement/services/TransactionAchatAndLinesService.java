package com.projet.equipement.services;

import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.mapper.LigneAchatMapper;
import com.projet.equipement.repository.AchatRepository;
import com.projet.equipement.repository.LigneAchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionAchatAndLinesService {

    private final ProduitService produitService;
    private final LigneAchatMapper ligneAchatMapper;
    private final LigneAchatRepository ligneAchatRepository;
    private final MouvementStockService mouvementStockService;
    private final AchatMapper achatMapper;
    private final AchatRepository achatRepository;
    private final EmployeService employeService;

    public TransactionAchatAndLinesService(ProduitService produitService, LigneAchatMapper ligneAchatMapper, LigneAchatRepository ligneAchatRepository, MouvementStockService mouvementStockService, AchatMapper achatMapper, AchatRepository achatRepository, EmployeService employeService) {
        this.produitService = produitService;
        this.ligneAchatMapper = ligneAchatMapper;
        this.ligneAchatRepository = ligneAchatRepository;
        this.mouvementStockService = mouvementStockService;
        this.achatMapper = achatMapper;
        this.achatRepository = achatRepository;
        this.employeService = employeService;
    }


    @Transactional
    public void updateTotalAchat(Long achatId) {
        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new EntityNotFoundException("Achat", achatId));

        Double total = ligneAchatRepository.sumTotalByAchatId(achatId);
        achat.setMontantTotal(total != null ? total : 0.0);

        achatRepository.save(achat);
    }


    @Transactional
    public void softDeleteAchat(Long id){

        List<LigneAchat> ligneAchats = this.findByAchatId(id);
        for (LigneAchat ligneAchat : ligneAchats) {
            this.deleteLinesByIdSoft(ligneAchat.getId());
        }

        this.deleteAchatByIdSoft(id);
    }

    public Page<LigneAchat> findAllLine(Pageable pageable) {
        return ligneAchatRepository.findAllLine(pageable);
    }

    public LigneAchat findByIdLine(Long id) {
        return ligneAchatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneAchat", id));
    }


    /**
     * Soft delete
     * @param id id de la ligne
     */
    @Transactional
    public void deleteLinesByIdSoft(Long id) {
        // cet id est l'id de la ligne d'achat
        LigneAchat ligneAchat = this.findByIdLine(id);
        String reference = "ACH_" + ligneAchat.getAchat().getId() + "_LIG_" + ligneAchat.getId() + "_DEL";


        LocalDateTime dateCreate = LocalDateTime.now();

        MouvementStockPostDto mvtInverse = MouvementStockPostDto.builder()
                .reference(reference)
                .produitId(ligneAchat.getProduit().getId())
                .quantite(ligneAchat.getQuantite())
                .commentaire("Eve inverse pour ajuster le stock ")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("RETOUR_FOURNISSEUR")
                .idEvenementOrigine(Math.toIntExact(ligneAchat.getAchat().getId()))
                .idLigneOrigine(Math.toIntExact(ligneAchat.getId()))
                .build();
        // soft delete du mvt
        mouvementStockService.save(mvtInverse);

        // soft delete de la ligne
        ligneAchat.setActif(false);
        ligneAchatRepository.save(ligneAchat);
        updateTotalAchat(ligneAchat.getAchat().getId());

    }

    /**
     * Obtient les lignes de l'achat par l'id de l'achat
     *
     * @param id id achat
     * @return Page de lignes
     */
    public Page<LigneAchat> findByAchatId(Long id, Pageable pageable) {
        return ligneAchatRepository.findByAchatId(id, pageable);
    }

    /**
     * Obtient les lignes de l'achat par l'id de l'achat
     *
     * @param id id achat
     * @return List de ligne
     */
    public List<LigneAchat> findByAchatId(Long id) {
        return ligneAchatRepository.findByAchatId(id);
    }


    public Page<Achat> findAllAchat(Pageable pageable) {
        return achatRepository.findAllPage(pageable);
    }

    public Achat findByIdAchat(Long id) {
        return achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
    }

    // Suppression de l'achat
    public void deleteAchatByIdSoft(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
        achat.setActif(false);  // Soft delete
        achatRepository.save(achat);
    }

    @Transactional
    public LigneAchat saveLigneAchat(LigneAchatPostDto ligneAchatPostDto) {
        Achat achat = null;
        if(ligneAchatPostDto.getAchatId() != null) {
            achat = this.findByIdAchat(Long.valueOf(ligneAchatPostDto.getAchatId()));
        }
        Produit produit = null;
        if(ligneAchatPostDto.getProduitId() != null) {
            produit = produitService.findById(Long.valueOf(ligneAchatPostDto.getProduitId()));
        }

        LigneAchat ligneAchatToPost = ligneAchatMapper.postLigneAchatFromDto(ligneAchatPostDto, achat, produit);

        LigneAchat saveLigneAchat = ligneAchatRepository.save(ligneAchatToPost);

        // Modifie le total de l'achat lors de la save d'une ligne

        updateTotalAchat(saveLigneAchat.getAchat().getId());

        LocalDateTime dateCreate = LocalDateTime.now();
        // Enregistrement du mouvement de stock via le service dédié
        mouvementStockService.save(MouvementStockPostDto.builder()
                .reference( "ACH_" + ligneAchatPostDto.getAchatId() + "_LIG_" + saveLigneAchat.getId())
                .produitId(Long.valueOf(ligneAchatPostDto.getProduitId()))
                .quantite(ligneAchatPostDto.getQuantite())
                .commentaire("Généré à partir de la ligne d'un achat")
                .createdAt(dateCreate)
                .dateMouvement(dateCreate)
                .typeMouvementCode("ACHAT_MARCHANDISE")
                .idEvenementOrigine(Math.toIntExact(saveLigneAchat.getAchat().getId()))
                .idLigneOrigine(Math.toIntExact(saveLigneAchat.getId()))
                .build());


        return saveLigneAchat;
    }

    @Transactional
    public Achat saveAchat(AchatPostDto achatPostDto) {
//        Set<Role> roles = achat.getRoles();
        Employe employe = null;
        if (achatPostDto.getEmployeId() != null) {
            employe = employeService.findById(achatPostDto.getEmployeId());
        }
        Achat achat =  achatMapper.postAchatDto(achatPostDto, employe);

        // Sauvegarde de l'achat pour obtenir l'id
        Achat achatSansTotal = achatRepository.save(achat);
        // Mise a jour du total
        updateTotalAchat(achatSansTotal.getId());

        return achatSansTotal;
    }

    @Transactional
    public LigneAchat updateLigneAchat(LigneAchatUpdateDto ligneAchatUpdateDto, Long id) {
        LigneAchat ligneAchat = this.findByIdLine(id);

        Achat achat = null;
        if(ligneAchatUpdateDto.getAchatId() != null) {
            achat = this.findByIdAchat(Long.valueOf(ligneAchatUpdateDto.getAchatId())) ;
        }
        Produit produit = null;
        if(ligneAchatUpdateDto.getProduitId() != null) {
            produit = produitService.findById(Long.valueOf(ligneAchatUpdateDto.getProduitId())) ;
        }
        ligneAchatMapper.updateLigneAchatFromDto(ligneAchatUpdateDto, ligneAchat, achat, produit);
        LigneAchat savedLine = ligneAchatRepository.save(ligneAchat);
        //mise a jour du total
        updateTotalAchat(savedLine.getAchat().getId());
        return savedLine;
    }


    /**
     * Pour la mise à jour d'un achat
     * @param achatUpdateDto
     * @param id
     * @return
     */
    public Achat updateAchat(AchatUpdateDto achatUpdateDto, Long id) {
        Achat achat = this.findByIdAchat(id);
        Employe employe = null;
        if (achatUpdateDto.getEmployeId() != null){
            employe = employeService.findById(Long.valueOf(achatUpdateDto.getEmployeId()));
        }
        achatMapper.updateAchatFromDto(achatUpdateDto,achat, employe);
        return achatRepository.save(achat);
    }
}
