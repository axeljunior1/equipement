package com.projet.equipement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue
    @Column(unique=true, nullable=false, length=10)
    private Long id;
    @Column(nullable=false, length=100 )
    private String name;
    private String description;
    private String image;
    private String category;
    private String price;
    private String quantity;
    private String unit;
    private String status;
    private String type;
    private String location;


}
