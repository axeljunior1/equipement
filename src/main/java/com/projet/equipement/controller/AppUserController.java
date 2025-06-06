package com.projet.equipement.controller;

import com.projet.equipement.entity.payapp.AppUser;
import com.projet.equipement.services.payapp.AppUserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/app-users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getAppUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUser getAppUserById(Long id) {
        return appUserService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAppUserById(Long id) {
        appUserService.deleteUser(id);
    }
}
