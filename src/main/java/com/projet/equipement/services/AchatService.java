package com.projet.equipement.services;


import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.AchatMapper;
import com.projet.equipement.repository.AchatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchatService {
    private final AchatRepository achatRepository;
    private final AchatMapper achatMapper;
    private final EmployeService employeService;
    private final ClientService clientService;

    public AchatService(AchatRepository achatRepository, AchatMapper achatMapper, EmployeService employeService, ClientService clientService) {
        this.achatRepository = achatRepository;
        this.achatMapper = achatMapper;
        this.employeService = employeService;
        this.clientService = clientService;
    }

    public List<Achat> findAll() {
        return achatRepository.findAll();
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
        achatRepository.deleteById(id);
    }



    public Achat updateAchat(AchatUpdateDto achatUpdateDto, Long id) {
        Achat achat = findById(id);
//        Set<Role> roles = new HashSet<>();
        achatMapper.updateAchatFromDto(achatUpdateDto,achat, employeService);
        return achatRepository.save(achat);
    }

    // Add a role to a user
//    public void addRoleToUser(Long userId, Long roleId) {
//        Achat achat = findById(userId);
//        Role role = roleRepository.findById(roleId)
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        // Add the role to the user
//        achat.getRoles().add(role);
//        achatRepository.save(achat);
//    }

}
