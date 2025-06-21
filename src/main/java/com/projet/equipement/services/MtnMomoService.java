package com.projet.equipement.services;

import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.ModePaimentRepository;
import com.projet.equipement.repository.PaiementRepository;
import com.projet.equipement.repository.VenteRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class MtnMomoService {

    private final MomoTokenService momoTokenService;


    private final RestTemplate rest = new RestTemplate();
    private final EtatPaiementService etatPaiementService;
    private final PaiementRepository paiementRepository;
    private final ModePaimentRepository modePaimentRepository;
    private final VenteRepository venteRepository;
    private final VenteService venteService;

    public MtnMomoService(MomoTokenService momoTokenService, EtatPaiementService etatPaiementService, PaiementRepository paiementRepository, ModePaimentRepository modePaimentRepository, VenteRepository venteRepository, VenteService venteService) {
        this.momoTokenService = momoTokenService;
        this.etatPaiementService = etatPaiementService;
        this.paiementRepository = paiementRepository;
        this.modePaimentRepository = modePaimentRepository;
        this.venteRepository = venteRepository;
        this.venteService = venteService;
    }


    public boolean initierPaiement(PaiementRequestMomo request) {

        String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay";
        System.out.println(momoTokenService.getValidAccessToken() + " " + request.getNumero() + " " + request.getMontant() + " " + request.getReferenceId() + " " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(momoTokenService.getValidAccessToken());
        headers.set("X-Reference-Id", request.getReferenceId());
        headers.set("X-Target-Environment", "sandbox");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", "94d55ca5ca9743cd809cebfdfed5e90f");

        Map<String, Object> body = Map.of(
                "amount", request.getMontant(),
                "currency", "EUR",
                "externalId", "123456",
                "payer", Map.of("partyIdType", "MSISDN", "partyId", request.getNumero()),
                "payerMessage", "Paiement POS",
                "payeeNote", "Merci"
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            rest.postForEntity(url, entity, Void.class);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur MTN : " + e.getMessage());
            return false;
        }
    }


    @Transactional
    public String getStatut(StatusMomoRequest statusMomoRequest) {
        String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay/" + statusMomoRequest.getRefId();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(momoTokenService.getValidAccessToken());
        headers.set("X-Target-Environment", "sandbox");
        headers.set("Ocp-Apim-Subscription-Key", "94d55ca5ca9743cd809cebfdfed5e90f");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> res = rest.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> body = res.getBody();
            if (body == null) return "ERREUR";

            String status = (String) body.get("status");

            // Ne sauvegarder que si la transaction est terminée
            if (!"PENDING".equalsIgnoreCase(status)) {

                PaiementRequest paiment = new PaiementRequest();
                paiment.setModePaiementId(modePaimentRepository.findByCode("Mobile Money")
                        .orElseThrow(
                                () -> new EntityNotFoundException("Mode de paiement", "Mobile Money")).getId()
                );
                paiment.setMontantPaiement(new BigDecimal((String) body.get("amount")));

                venteService.payer(statusMomoRequest.getVenteId(), paiment);


            }

            return status;

        } catch (Exception e) {
            System.out.println("Erreur statut : " + e.getMessage());
            return "ERREUR";
        }
    }
}

