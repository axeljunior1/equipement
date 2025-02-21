package com.projet.equipement.utils;

import java.time.Year;
import java.util.concurrent.atomic.AtomicInteger;

public class FactureNumeroGenerator {

    private static AtomicInteger counter = new AtomicInteger(1); // Compteur initialisé à 1

    public static String generateNumeroFacture() {
        int year = Year.now().getValue(); // Année actuelle (ex: 2025)
        int number = counter.getAndIncrement(); // Incrémente et récupère le numéro actuel

        // Formatage du numéro de facture avec 4 chiffres pour le numéro
        return String.format("FAC-%d-%04d", year, number);
    }

    public static void resetCounter() {
        counter.set(1); // Réinitialise le compteur à 1 à chaque début d'année
    }
}
