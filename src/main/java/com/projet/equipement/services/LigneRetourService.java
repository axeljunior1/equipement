package com.projet.equipement.services;


import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.entity.LigneRetour;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Retour;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.LigneRetourMapper;
import com.projet.equipement.repository.EtatRetourRepository;
import com.projet.equipement.repository.LigneRetourRepository;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.RetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LigneRetourService {


    private final LigneRetourRepository ligneRetourRepository;
    private final LigneRetourMapper ligneRetourMapper;
    private final RetourRepository retourRepository;
    private final LigneVenteRepository ligneVenteRepository;
    private final EtatRetourRepository etatRetourRepository;
    private final VenteService venteService;

    public LigneRetourService(LigneRetourRepository ligneRetourRepository,
                              LigneRetourMapper ligneRetourMapper,
                              RetourRepository retourRepository,
                              LigneVenteRepository ligneVenteRepository, EtatRetourRepository etatRetourRepository, VenteService venteService) {
        this.ligneRetourRepository = ligneRetourRepository;
        this.ligneRetourMapper = ligneRetourMapper;
        this.retourRepository = retourRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.etatRetourRepository = etatRetourRepository;
        this.venteService = venteService;
    }

    public LigneRetourGetDto findById(Long id) {
        return ligneRetourMapper.toDto(ligneRetourRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("LigneRetour", id)));
    }

    // ligneRetourne tout
    public Page<LigneRetourGetDto> findAll(Pageable pageable) {
        Page<LigneRetour> all = ligneRetourRepository.findAll(pageable);
        return all.map(ligneRetourMapper::toDto);
    }

    //save
    public LigneRetourGetDto save(LigneRetour ligneRetour) {
        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }


    //save
    public LigneRetourGetDto save(LigneRetourPostDto ligneRetourPostDto) {
        Retour retour = retourRepository.findById(ligneRetourPostDto.getRetourId()).orElseThrow(
                () -> new EntityNotFoundException("Retour", ligneRetourPostDto.getRetourId()));
        LigneVente ligneVente = ligneVenteRepository.findById(ligneRetourPostDto.getLigneVenteId()).orElseThrow(
                () -> new EntityNotFoundException("LigneVente", ligneRetourPostDto.getLigneVenteId()));

         Integer qteVente = ligneVente.getQuantite();
         if (ligneRetourPostDto.getQuantite()>qteVente) {
             throw new RuntimeException(
                     "La quantité retournée est superieur à la qté vendu"
             );
         }

        LigneRetour ligneRetour = ligneRetourMapper.toEntity(ligneRetourPostDto);

        ligneRetour.setRetour(retour);
        ligneRetour.setLigneVente(ligneVente);

        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }

    public void saveAll(List<LigneRetourPostDto> ligneRetourPostDtos) {

        // SI LA QUANTITE EST SUPERIEUR A LA QTE DANS LA VENTE ALORS ON RETOURNE UNE EXCEPTION


        if (!ligneRetourPostDtos.isEmpty()) {
            ligneRetourPostDtos.forEach(ligneRetourPostDto -> {

                Optional<LigneRetour> ligneRetour = ligneRetourRepository.findByRetour_IdAndLigneVente_Id(
                        ligneRetourPostDto.getRetourId(),
                        ligneRetourPostDto.getLigneVenteId());
                if (ligneRetour.isEmpty() ) {
                    save(ligneRetourPostDto);
                } else if (Objects.equals(ligneRetour.get().getRetour().getEtat().getLibelle(), "EN_ATTENTE_VALIDATION")){
                    LigneVente lv = ligneRetour.get().getLigneVente();
                    Integer qteVente = lv.getQuantite();
                    if (ligneRetour.get().getQuantite()>qteVente) {
                        throw new RuntimeException("La qtantité retournée est superieur à la qté vendu");
                    }
                    ligneRetour.get().setQuantite(ligneRetourPostDto.getQuantite());
                    ligneRetourRepository.save(ligneRetour.get());
                }else {
                    throw new RuntimeException("L'etat de la vente ne le permet pas");
                }
            });
        }
    }

    //modifier
    public LigneRetourGetDto update(LigneRetourUpdateDto ligneRetourUpdateDto, Long id) {
        LigneRetour ligneRetour = ligneRetourRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LigneRetour", id));


        Retour retour = retourRepository.findById(ligneRetourUpdateDto.getRetourId()).orElseThrow(
                () -> new EntityNotFoundException("Retour", ligneRetourUpdateDto.getRetourId()));
        LigneVente ligneVente = ligneVenteRepository.findById(ligneRetourUpdateDto.getLigneVenteId()).orElseThrow(
                () -> new EntityNotFoundException("LigneVente", ligneRetourUpdateDto.getLigneVenteId()));

        ligneRetourMapper.updateDto(ligneRetourUpdateDto, ligneRetour);

        ligneRetour.setRetour(retour);
        ligneRetour.setLigneVente(ligneVente);

        return ligneRetourMapper.toDto(ligneRetourRepository.save(ligneRetour));
    }

    public boolean deleteById(Long id) {
        LigneRetour ligneRetour = ligneRetourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LigneRetour", id));

        String retourLib = ligneRetour.getRetour().getEtat().getLibelle();

        if (retourLib != null && (retourLib.equals("TRAITE") || retourLib.equals("EN_ATTENTE_VALIDATION"))) {
            ligneRetourRepository.deleteById(id);
            return true; // suppression effectuée
        }
        return false; // suppression refusée
    }



    public Page<LigneRetourGetDto> findByRetourId(Long id, Pageable pageable) {
        Page<LigneRetour> ligneRetours = ligneRetourRepository.findByRetour_Id(id, pageable);
        return ligneRetours.map(ligneRetourMapper::toDto);
    }
}
