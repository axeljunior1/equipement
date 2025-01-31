package com.projet.equipement.controller;


import com.projet.equipement.entity.Produit;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.security.JwtUtil;
import com.projet.equipement.services.MouvementStockService;
import com.projet.equipement.services.ProduitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProduitController.class) // Test uniquement du contrôleur
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduitService produitService; // Mock du service
    @MockBean
    private MouvementStockService mouvementStockService; // Mock du service
    @MockBean
    private MouvementStockRepository mouvementStockRepository; // Mock du service

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testFindProduitById_Success() throws Exception {
        String token = jwtUtil.generateToken("admin");
        // Mock du produit retourné par le service
        Produit produit = Produit.builder()
                .id(1L)
                .nom("Horloge")
                .description("Horloge connecté")
                .image("")
                .stockInitial(10)
                .prixUnitaire(29.99)
                .ean13("9994384219015")
                .qrCode("iVBORw0KGgoAAAANSUhEUgAAAGQAAABkAQAAAABYmaj5AAAAv0lEQVR4XuXSsQ3EIAwFUJ8orswCSKyRLiuFBS66BY6V3HkNpCwQOgokH9EdIQ0OfSyaV9n+BvhUG9xNCA9MM8ByKeIQk+XUIbCkLeo+raFTaPrEgfOrkzWV9yN93rapXNEcmUlCFci4MosoP3B6jaWfIOLtmaDeoS1Wn1EPsUPkZ1gddoj3Ji6q336ScmZs3liSELTfAWDyZQdBYFltY720JOQPrP8OF1Iumg5xyGFMR9Zt7f/FL1STaKrWrfUFsIoVnBVamIsAAAAASUVORK5CYII=".getBytes())
                .build();

        when(produitService.findById(1L)).thenReturn(produit);

        // Exécuter la requête et vérifier la réponse
        mockMvc.perform(get("/produits/1"))
                .andExpect(status().isOk()) // Vérifie le code 200
                .andExpect(jsonPath("$.nom").value("Horloge"));

        // Vérifier que la méthode a été appelée
        verify(produitService, times(1)).findById(1L);
    }

    @Test
    void testFindProduitById_NotFound() throws Exception {
        when(produitService.findById(2L)).thenThrow(new RuntimeException("Produit non trouvé"));

        mockMvc.perform(get("/produits/2"))
                .andExpect(status().isNotFound()) // Vérifie le code 404
                .andExpect(content().string("Produit non trouvé"));
    }
}
