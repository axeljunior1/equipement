package com.projet.equipement.service;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.services.ProduitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitService produitService;  // Service sous test

    private Produit produit;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setId(1L);
        produit.setEan13("9991234567890");
        produit.setNom("Ordinateur Portable");
    }

    @Test
    void testFindById_Success(){

        // Simuler que le produit avec ID 1 existe en base
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

        // Appel de la méthode de service
        Produit foundProduit = produitService.findById(1L);

        // Vérifications
        assertNotNull(foundProduit);
        assertEquals(1L, foundProduit.getId());
        assertEquals("Ordinateur Portable", foundProduit.getNom());


        // Vérifier que findById() a été appelé une seule fois
        verify(produitRepository, times(1)).findById(1L);

    }

    @Test
    void testFindByEan13_Success(){

        // Simuler que le produit avec ID 1 existe en base
        String ean13 = "9991234567890";
        when(produitRepository.findByEan13(ean13)).thenReturn(Optional.of(produit));

        // Appel de la méthode de service
        Produit foundProduit = produitService.findByEan13(ean13);

        // Vérifications
        assertNotNull(foundProduit);
        assertEquals(ean13, foundProduit.getEan13());
        assertEquals("Ordinateur Portable", foundProduit.getNom());


        // Vérifier que findById() a été appelé une seule fois
        verify(produitRepository, times(1)).findByEan13(ean13);

    }

    @Test
    void testFindById_NotFound() {
        // Simuler qu'aucun produit n'est trouvé
        long IdProduit = 2L;
        when(produitRepository.findById(IdProduit)).thenReturn(Optional.empty());

        // Vérifier que l'exception est bien levée
        Exception exception = assertThrows(RuntimeException.class, () -> {
            produitService.findById(IdProduit);
        });

        assertEquals("La resource Produit avec l'identifiant "+ IdProduit +" n'a pas été trouvée", exception.getMessage());

        // Vérifier que findById() a été appelé une seule fois
        verify(produitRepository, times(1)).findById(IdProduit);
    }

}
