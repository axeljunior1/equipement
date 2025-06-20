package com.projet.equipement.services;


import com.projet.equipement.entity.TypeRetour;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.TypeRetourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TypeRetourService {


    private final TypeRetourRepository typeRetourRepository;

    public TypeRetourService(TypeRetourRepository typeRetourRepository) {
        this.typeRetourRepository = typeRetourRepository;
    }

    public TypeRetour findById(Long id){
        return typeRetourRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("TypeRetour", id));
    }

    // retourne tout
    public Page<TypeRetour> findAll(Pageable pageable){
        return typeRetourRepository.findAll(pageable);
    }

    //save
    public TypeRetour save(TypeRetour retour){
        return typeRetourRepository.save(retour);
    }

    //modifier
    public TypeRetour update(TypeRetour retour, Long id){
        TypeRetour retour1 = findById(id);
        retour1.setDescription(retour.getDescription());
        return typeRetourRepository.save(retour1);
    }

    //delete
    public void deleteById(Long id){
        typeRetourRepository.deleteById(id);
    }


}
