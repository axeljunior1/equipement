package com.projet.equipement.services;


import com.projet.equipement.dto.formatVente.FormatVenteGetDto;
import com.projet.equipement.dto.formatVente.FormatVentePostDto;
import com.projet.equipement.dto.formatVente.FormatVenteUpdateDto;
import com.projet.equipement.entity.FormatVente;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.entity.UniteVente;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.FormatVenteMapper;
import com.projet.equipement.repository.FormatVenteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FormatVenteService {


    private final FormatVenteRepository formatVenteRepository;
    private final ProduitService produitService;
    private final UniteVenteService uniteVenteService;
    private final FormatVenteMapper formatVenteMapper;

    public FormatVenteService(FormatVenteRepository formatVenteRepository, ProduitService produitService, UniteVenteService uniteVenteService, FormatVenteMapper formatVenteMapper) {
        this.formatVenteRepository = formatVenteRepository;
        this.produitService = produitService;
        this.uniteVenteService = uniteVenteService;
        this.formatVenteMapper = formatVenteMapper;
    }

    public Page<FormatVente> findAll(Pageable pageable) {
        return formatVenteRepository.findAll(pageable);
    }
    
    public FormatVente findById(Long id) {
        return formatVenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Format de vente non trouvé"));
    }



    public Page<FormatVente> findAllByProduitId(Long id, Pageable pageable) {
        return formatVenteRepository.findByProduitId(id, pageable);
    }
    
    public FormatVenteGetDto save(FormatVentePostDto formatVentePostDto) {

        Produit p = produitService.findById(formatVentePostDto.getProduitId());

        UniteVente uv = uniteVenteService.findById(formatVentePostDto.getUniteVenteId());

        FormatVente formatVente = formatVenteMapper.toEntity(formatVentePostDto);

        formatVente.setProduit(p);
        formatVente.setUniteVente(uv);
        formatVente.setTenantId(TenantContext.getTenantId());

        FormatVente save = formatVenteRepository.save(formatVente);

        return formatVenteMapper.toDto(save);
    }

    public void deleteById(Long id) {
        formatVenteRepository.deleteById(id);
    }

    public FormatVenteGetDto update(Long id, @Valid FormatVenteUpdateDto formatVenteUpdateDto) {
        FormatVente formatVente = formatVenteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("FormatVente", id));

        formatVenteMapper.updateDto(formatVenteUpdateDto, formatVente);

        return formatVenteMapper.toDto(formatVenteRepository.save(formatVente));
    }
}