package com.projet.equipement.dto.client;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientUpdateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;


}
