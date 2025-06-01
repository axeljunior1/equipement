package com.projet.equipement.services;


import com.projet.equipement.entity.MomoToken;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.repository.MomoTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MomoTokenService {
    @Autowired
    private MomoTokenRepository tokenRepo;

    @Autowired
    private MomoApiClient momoApiClient;

    public String getValidAccessToken() {
        Optional<MomoToken> tokenOpt = tokenRepo.findTopByOrderByCreatedAtDesc();
        if (tokenOpt.isPresent() && tokenOpt.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return tokenOpt.get().getAccessToken();
        }

        // Sinon : en générer un nouveau
        MomoToken newToken = momoApiClient.fetchNewToken(); // appel HTTP à /token
        newToken.setTenantId(TenantContext.getTenantId());
        return tokenRepo.save(newToken).getAccessToken();
    }
}
