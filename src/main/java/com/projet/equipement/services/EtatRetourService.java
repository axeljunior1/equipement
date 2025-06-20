package com.projet.equipement.services;


import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EtatRetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EtatRetourService {


    private final EtatRetourRepository etatRetourRepository;

    public EtatRetourService(EtatRetourRepository etatRetourRepository) {
        this.etatRetourRepository = etatRetourRepository;
    }

    public EtatRetour findById(Long id){
        return etatRetourRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("EtatRetour", id));
    }

    // retourne tout
    public Page<EtatRetour> findAll(Pageable pageable){
        return etatRetourRepository.findAll(pageable);
    }

    //save
    public EtatRetour save(EtatRetour retour){
        return etatRetourRepository.save(retour);
    }

    //modifier
    public EtatRetour update(EtatRetour retour, Long id){
        EtatRetour retour1 = findById(id);
        retour1.setDescription(retour.getDescription());
        return etatRetourRepository.save(retour1);
    }

    //delete
    public void deleteById(Long id){
        etatRetourRepository.deleteById(id);
    }


}
