package com.projet.equipement.services;

import com.projet.equipement.entity.MomoToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

@Component
public class MomoApiClient {

    @Value("${momo.api.userId}")
    private String apiUser;

    @Value("${momo.api.apiKey}")
    private String apiKey;

    private String tokenUrl= "https://sandbox.momodeveloper.mtn.com/collection/token/";

    private final RestTemplate restTemplate = new RestTemplate();

    public MomoToken fetchNewToken() {
        // Encode les identifiants en Base64
        String auth = apiUser + ":" + apiKey;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        // Création de l'en-tête Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.set("Ocp-Apim-Subscription-Key", "94d55ca5ca9743cd809cebfdfed5e90f"); // à mettre aussi en @Value
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        Map body = response.getBody();
        String accessToken = (String) body.get("access_token");
        Integer expiresIn = (Integer) body.get("expires_in");

        // Construction de l'objet MomoToken à enregistrer
        MomoToken token = new MomoToken();
        token.setAccessToken(accessToken);
        token.setTokenType((String) body.getOrDefault("token_type", "Bearer"));
        token.setExpiresAt(LocalDateTime.now().plusSeconds(expiresIn));
        return token;
    }
}
