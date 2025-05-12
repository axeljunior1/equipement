package com.projet.equipement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class EquipementApplication {

    public static void main(String[] args) {

        SpringApplication.run(EquipementApplication.class, args);
    }

}
