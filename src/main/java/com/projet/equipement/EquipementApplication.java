package com.projet.equipement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EquipementApplication {

    public static void main(String[] args) {

//        System.out.println(CodeBarreUtils.generateEAN13WithPrefix("9"));

        SpringApplication.run(EquipementApplication.class, args);
    }

}
