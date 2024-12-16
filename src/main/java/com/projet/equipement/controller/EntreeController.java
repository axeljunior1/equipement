package com.projet.equipement.controller;

import com.projet.equipement.dto.entree.EntreePostDto;
import com.projet.equipement.dto.entree.EntreeUpdateDto;
import com.projet.equipement.dto.mouvementStock.MouvementStockPostDto;
import com.projet.equipement.entity.Entree;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.TypeMouvement;
import com.projet.equipement.services.EntreeService;
import com.projet.equipement.services.MouvementStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/entree")
@RestController
public class EntreeController {

    private final EntreeService entreeService;
    private final MouvementStockService mouvementStockService;

    public EntreeController(EntreeService entreeService, MouvementStockService mouvementStockService) {
        this.entreeService = entreeService;
        this.mouvementStockService = mouvementStockService;
    }


    @GetMapping("")
    public ResponseEntity<List<Entree>> findAllEntrees() {
        List<Entree> entrees = entreeService.findAll();
        return ResponseEntity.ok(entrees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entree> findEntree(@PathVariable Long id) {
        Entree entree = entreeService.findById(id);
        return ResponseEntity.ok(entree);
    }

    @PostMapping("")
    public ResponseEntity<Entree> addEntree(@RequestBody EntreePostDto entreePostDto) {
        Entree entree  = entreeService.createEntree(entreePostDto);

        // cree un dto de Post Mouvement de stock  avec l'entree créée
        MouvementStockPostDto mvtStockDto = MouvementStockPostDto.builder()
                .entreeId(entree.getId())
                .produitId(entree.getProduit().getId())
                .utilisateurId(entree.getUtilisateur().getId())
                .sortieId(null)
                .quantity(entree.getQuantite())
                .typeMouvement(TypeMouvement.ENTREE)
                .build();

        //save le post
        MouvementStock mouvementStockSave = mouvementStockService.save(mvtStockDto) ;

        // Cree une entree dto Update abec uniquement l'id du mouvement
        EntreeUpdateDto entreeUpdateDto = EntreeUpdateDto.builder()
                .mouvementId(mouvementStockSave.getId())
                .build();

        // Update l'entree pour lui donner le mouvement
        Entree entreeFinal = entreeService.updateEntree(entree.getId(), entreeUpdateDto ) ;

        return ResponseEntity.ok(entreeFinal);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Entree> updateEntree(@PathVariable Long id ,@RequestBody EntreeUpdateDto entreeUpdateDto) {
        Entree entree = entreeService.updateEntree(id, entreeUpdateDto);
        return ResponseEntity.ok(entree);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntree(@PathVariable Long id) {
        entreeService.deleteById(id);
        return ResponseEntity.ok("Entree deleted");
    }


}
