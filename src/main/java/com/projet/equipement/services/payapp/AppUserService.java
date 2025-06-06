package com.projet.equipement.services.payapp;

import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.entity.payapp.AppUser;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.payapp.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser createUser(AppUser user) {
        user.setTenantId(TenantContext.getTenantId());
        return appUserRepository.save(user);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("AppUser", id));
    }

    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }
}
