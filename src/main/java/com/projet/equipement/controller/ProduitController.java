package com.projet.equipement.controller;

import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.specifications.ProduitSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/produits")
public class ProduitController {


    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitGetDto> findProduitById(@PathVariable Long id) {
        ProduitGetDto produitGetDto = produitService.findProduitDtoById(id);
        return ResponseEntity.ok(produitGetDto);
    }

    @GetMapping("/code-barre/{ean13}")
    public ResponseEntity<ProduitGetDto> findProduitByEan13(@PathVariable String ean13) {
        Produit produit = produitService.findByEan13(ean13);
        return ResponseEntity.ok(new ProduitGetDto(produit));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProduitGetDto>> findAllProduit( Pageable pageable) {

          ProduitGetDto[] PRODUITS = {
                new ProduitGetDto(1L, "Chargeur rapide USB-C", "Chargeur universel 20W pour smartphones et tablettes.", "chargeur.jpg", 50, 19.99, 1, 5, 100, LocalDateTime.now()),
                new ProduitGetDto(2L, "Coque iPhone 13", "Coque transparente en silicone pour iPhone 13.", "coque_iphone.jpg", 80, 12.99, 1, 10, 200, LocalDateTime.now()),
                new ProduitGetDto(3L, "Écouteurs sans fil", "Écouteurs Bluetooth avec réduction de bruit et autonomie 24h.", "ecouteurs.jpg", 40, 89.99, 1, 5, 80, LocalDateTime.now()),
                new ProduitGetDto(4L, "Lampe de chevet LED", "Lampe moderne avec réglage tactile et intensité variable.", "lampe_led.jpg", 30, 34.99, 2, 3, 50, LocalDateTime.now()),
                new ProduitGetDto(5L, "Miroir mural design", "Miroir rond avec cadre doré élégant pour décoration intérieure.", "miroir.jpg", 15, 49.99, 2, 2, 30, LocalDateTime.now()),
                new ProduitGetDto(6L, "Aspirateur sans fil", "Aspirateur balai puissant avec batterie longue durée.", "aspirateur.jpg", 10, 179.99, 2, 1, 20, LocalDateTime.now()),
                new ProduitGetDto(7L, "Couteau de chef", "Couteau professionnel en acier inoxydable avec manche ergonomique.", "couteau.jpg", 40, 24.99, 4, 5, 75, LocalDateTime.now()),
                new ProduitGetDto(8L, "Mixeur plongeant", "Mixeur 3 en 1 pour soupes, sauces et smoothies.", "mixeur.jpg", 25, 39.99, 4, 5, 50, LocalDateTime.now()),
                new ProduitGetDto(9L, "Fer à repasser vapeur", "Fer vapeur puissant avec semelle en céramique anti-adhésive.", "fer.jpg", 15, 39.99, 4, 3, 30, LocalDateTime.now()),
                new ProduitGetDto(10L, "Télévision 55\" 4K", "TV UHD 4K avec HDR et assistant vocal intégré.", "tv_4k.jpg", 10, 499.99, 5, 1, 20, LocalDateTime.now()),
                new ProduitGetDto(11L, "Support mural TV", "Support inclinable et orientable pour écran jusqu'à 65\".", "support_tv.jpg", 30, 29.99, 5, 5, 50, LocalDateTime.now()),
                new ProduitGetDto(12L, "Écran gaming 27\"", "Moniteur 144Hz avec technologie FreeSync et résolution QHD.", "ecran_gaming.jpg", 12, 299.99, 6, 2, 25, LocalDateTime.now()),
                new ProduitGetDto(13L, "Projecteur Full HD", "Vidéo projecteur portable avec correction automatique.", "projecteur.jpg", 8, 399.99, 6, 1, 15, LocalDateTime.now()),
                new ProduitGetDto(14L, "PC portable i7 16Go RAM", "Ordinateur portable puissant avec SSD 512Go.", "pc_portable.jpg", 5, 899.99, 7, 2, 10, LocalDateTime.now()),
                new ProduitGetDto(15L, "Clavier mécanique RGB", "Clavier gaming avec switches mécaniques et rétroéclairage.", "clavier.jpg", 20, 79.99, 7, 5, 40, LocalDateTime.now()),
                new ProduitGetDto(16L, "Souris gaming sans fil", "Souris ergonomique avec capteur haute précision.", "souris.jpg", 25, 49.99, 7, 4, 50, LocalDateTime.now()),
                new ProduitGetDto(17L, "Tapis de souris XXL", "Tapis antidérapant pour gaming et bureautique.", "tapis_souris.jpg", 50, 19.99, 7, 5, 100, LocalDateTime.now()),
                new ProduitGetDto(18L, "Imprimante multifonction", "Imprimante laser avec scanner et connexion WiFi.", "imprimante.jpg", 10, 229.99, 7, 2, 15, LocalDateTime.now()),
                new ProduitGetDto(19L, "Disque dur externe 1To", "Stockage portable avec transfert rapide en USB 3.0.", "disque_dur.jpg", 35, 89.99, 7, 5, 60, LocalDateTime.now()),
                new ProduitGetDto(20L, "Enceinte Bluetooth", "Enceinte portable waterproof avec son stéréo.", "enceinte.jpg", 35, 59.99, 8, 5, 60, LocalDateTime.now()),
                new ProduitGetDto(21L, "Montre connectée", "Smartwatch avec suivi d'activité et notifications.", "montre.jpg", 18, 129.99, 8, 3, 30, LocalDateTime.now()),
                new ProduitGetDto(22L, "Caméra de sécurité WiFi", "Caméra intelligente avec détection de mouvement.", "camera.jpg", 15, 99.99, 8, 3, 30, LocalDateTime.now()),
                new ProduitGetDto(23L, "Climatiseur portable", "Climatiseur mobile avec fonction déshumidificateur.", "climatiseur.jpg", 5, 299.99, 2, 2, 10, LocalDateTime.now()),
                new ProduitGetDto(24L, "Cafetière à capsules", "Machine à café avec arrêt automatique.", "cafetiere.jpg", 20, 79.99, 4, 3, 40, LocalDateTime.now()),
                new ProduitGetDto(25L, "Four à micro-ondes", "Micro-ondes grill avec 10 programmes automatiques.", "micro_ondes.jpg", 15, 119.99, 4, 3, 30, LocalDateTime.now()),
                new ProduitGetDto(26L, "Câble HDMI 2m", "Câble HDMI 4K Ultra HD compatible TV et moniteurs.", "cable_hdmi.jpg", 75, 14.99, 1, 10, 150, LocalDateTime.now()),
                new ProduitGetDto(27L, "Chargeur sans fil rapide", "Chargeur Qi 15W pour smartphones compatibles.", "chargeur_sansfil.jpg", 30, 29.99, 1, 5, 60, LocalDateTime.now()),
                new ProduitGetDto(28L, "Casque audio Bluetooth", "Casque sans fil avec réduction de bruit active.", "casque.jpg", 25, 149.99, 8, 4, 50, LocalDateTime.now()),
                new ProduitGetDto(29L, "Table de bureau en bois", "Bureau spacieux avec pieds en métal.", "bureau.jpg", 10, 189.99, 2, 1, 20, LocalDateTime.now()),
                new ProduitGetDto(30L, "Fauteuil gaming ergonomique", "Chaise gaming avec support lombaire et accoudoirs réglables.", "chaise_gaming.jpg", 8, 259.99, 2, 1, 15, LocalDateTime.now()),
                // Ajoute 70 autres produits ici...
        };

        Page<ProduitGetDto> produitGetDtos = produitService.findByActif(true, pageable);
        return ResponseEntity.ok(produitGetDtos);
    }




    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherProduits(@RequestParam String motCle) {
        List<Produit> produits = produitService.rechercherProduits(motCle);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/recherche-dynamique")
    public List<Produit> filterProduits(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false) Integer stockInitialMin,
            @RequestParam(required = false) Integer stockInitialMax,
            @RequestParam(required = false) Double prixUnitaireMin,
            @RequestParam(required = false) Double prixUnitaireMax) {

        Specification<Produit> spec = Specification.where(ProduitSpecification.hasDescription(description))
                .and(ProduitSpecification.hasNom(nom))
                .and(ProduitSpecification.isActif(actif))
                .and(ProduitSpecification.hasStockBetween(stockInitialMin, stockInitialMax))
                .and(ProduitSpecification.hasPrixBetween(prixUnitaireMin, prixUnitaireMax));

        return produitService.findBySpec(spec);
    }

    @PostMapping
    public ResponseEntity<Produit> addProduit( @RequestBody  ProduitPostDto produitPostDto) {
        Produit produit = produitService.save(produitPostDto);
        return ResponseEntity.ok(produit);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody ProduitUpdateDto produitUpdateDto) {
        Produit produit = produitService.updateProduit(produitUpdateDto, id);
        System.out.println(produit);
        return ResponseEntity.ok(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteByIdSoft(id);
        return ResponseEntity.ok("Produit deleted");
    }

}

