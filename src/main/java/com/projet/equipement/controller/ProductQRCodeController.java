package com.projet.equipement.controller;


import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductQRCodeController {

    private final ProduitService produitService;

    public ProductQRCodeController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/api/products/{id}/qrcode")
    public ResponseEntity<byte[]> generateProductQRCode(@PathVariable Long id) {
       Produit produit = produitService.findById(id);

        byte[] qrCodeImage = produit.getQrCode();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");
        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    }
}
