package com.projet.equipement.services;

import com.projet.equipement.entity.PaiementRequest;
import com.projet.equipement.entity.Paiement;
import com.projet.equipement.entity.TenantContext;
import com.projet.equipement.repository.PaiementRepository;
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

    public MtnMomoService(MomoTokenService momoTokenService, EtatPaiementService etatPaiementService, PaiementRepository paiementRepository) {
        this.momoTokenService = momoTokenService;
        this.etatPaiementService = etatPaiementService;
        this.paiementRepository = paiementRepository;
    }


    public boolean initierPaiement(PaiementRequest request) {

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
    public String getStatut(String referenceId) {
        String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay/" + referenceId;

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
                Paiement transaction = new Paiement();

                transaction.setEtat(etatPaiementService.findByLibelle(Objects.equals(status, "SUCCESSFUL") || Objects.equals(status, "SUCCESS")   ? "PAYEE" : "ERREUR"));
                transaction.setTenantId(TenantContext.getTenantId());
                transaction.setUpdatedAt(LocalDateTime.now());
                transaction.setUpdatedAt(LocalDateTime.now());

                // Conversion de amount (String → BigDecimal)
                String amountStr = (String) body.get("amount");
                if (amountStr != null) {
                    transaction.setMontantPaye(new BigDecimal(amountStr));
                }

                // Extraction du numéro du client (payer.partyId)
//                Map<String, String> payer = (Map<String, String>) body.get("payer");
//                if (payer != null) {
//                    transaction.setClientPhone(payer.get("partyId"));
//                }

                paiementRepository.save(transaction);
            }

            return status;

        } catch (Exception e) {
            System.out.println("Erreur statut : " + e.getMessage());
            return "ERREUR";
        }
    }
}

