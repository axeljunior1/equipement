package com.projet.equipement.services;


import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService{

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final QrCodeService qrCodeService;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper, QrCodeService qrCodeService) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.qrCodeService = qrCodeService;
    }

    public List<Produit> findAll(){
        return produitRepository.findAll();
    }
    public  Produit findById(Long id){
        return  produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }

    public List<Produit> rechercherProduits(String motCle){
        return produitRepository.rechercherProduits(motCle);
    }

    public List<Produit> findBySpec(Specification<Produit> spec){
        return produitRepository.findAll(spec);
    }

    public Produit save(ProduitPostDto produitPostDto){
        Produit produit = produitMapper.PostProduitFromDto(produitPostDto);
        Produit produitSaved = produitRepository.save(produit);
        // set le qr code du produit
        produitSaved.setQrCode(qrCodeService.genAndSaveQrCodeByProduct(produit));
        return produitRepository.save(produit);
    }


    public Produit updateProduit(ProduitUpdateDto produitUpdateDto, Long id){
        Produit produit = findById(id);
        produitMapper.updateProduitFromDto(produitUpdateDto, produit);
        return produitRepository.save(produit);
    }

    public void deleteById(Long id){
        produitRepository.deleteById(id);
    }

}
