package com.projet.equipement.services;

import com.projet.equipement.entity.PaiementRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MtnMomoService {

    private final MomoTokenService momoTokenService;


    private final RestTemplate rest = new RestTemplate();
    public MtnMomoService(MomoTokenService momoTokenService) {
        this.momoTokenService = momoTokenService;
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

    public String getStatut(String referenceId) {
        String url = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay/" + referenceId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(momoTokenService.getValidAccessToken());
        headers.set("X-Target-Environment", "sandbox");
        headers.set("Ocp-Apim-Subscription-Key", "94d55ca5ca9743cd809cebfdfed5e90f");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> res = rest.exchange(url, HttpMethod.GET, entity, Map.class);
            return res.getBody().get("status").toString(); // "PENDING", "SUCCESSFUL", "FAILED"
        } catch (Exception e) {
            System.out.println("Erreur statut : " + e.getMessage());
            return "ERREUR";
        }
    }
}

