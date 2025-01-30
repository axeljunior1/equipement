package com.projet.equipement.services;


import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.repository.AchatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AchatService {
    private final AchatRepository achatRepository;
    private final AchatMapper achatMapper;
    private final EmployeService employeService;
    private final ClientService clientService;
    private final MouvementStockService mouvementStockService;

    public AchatService(AchatRepository achatRepository, AchatMapper achatMapper, EmployeService employeService, ClientService clientService, MouvementStockService mouvementStockService) {
        this.achatRepository = achatRepository;
        this.achatMapper = achatMapper;
        this.employeService = employeService;
        this.clientService = clientService;
        this.mouvementStockService = mouvementStockService;
    }

    public Page<Achat> findAll(Pageable pageable) {
        return achatRepository.findAll(pageable);
    }

    public Achat findById(Long id) {
        return achatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achat", id));
    }

    public Achat save(AchatPostDto achatPostDto) {
//        Set<Role> roles = achat.getRoles();
       Achat achat =  achatMapper.postAchatDto(achatPostDto, employeService);

        return achatRepository.save(achat);
    }

    public void deleteById(Long id) {

        String code = "ACHAT_MARCHANDISE";
        mouvementStockService.deleteByIdOrigineEveAndTypeMvtCode(id, code);
        achatRepository.deleteById(id);
    }



    public Achat updateAchat(AchatUpdateDto achatUpdateDto, Long id) {
        Achat achat = findById(id);
//        Set<Role> roles = new HashSet<>();
        achatMapper.updateAchatFromDto(achatUpdateDto,achat, employeService);
        return achatRepository.save(achat);
    }



}
