package com.projet.equipement.utils;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Random;

public class EAN13Generator {

    public byte[] genAndSaveQrCodeByProduct(String ean13){
        String qrContent = String.format("%s", ean13);
        try {

            return QRCodeGenerator.generateQRCodeImage(qrContent, 100, 100);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  String generateEAN13WithFirstChar(char firstChar) {
        Random random = new Random();

        // Commencer avec le premier caractère défini
        StringBuilder eanBuilder = new StringBuilder();
        eanBuilder.append(firstChar);

        // Générer les 11 chiffres restants aléatoirement
        for (int i = 0; i < 11; i++) {
            eanBuilder.append(random.nextInt(10)); // Ajouter un chiffre aléatoire (0-9)
        }

        // Calculer le chiffre de contrôle
        String eanWithoutChecksum = eanBuilder.toString();
        int checksum = calculateChecksum(eanWithoutChecksum);

        // Ajouter le chiffre de contrôle à la fin
        eanBuilder.append(checksum);

        return eanBuilder.toString();
    }

    private static int calculateChecksum(String eanWithoutChecksum) {
        int sum = 0;

        for (int i = 0; i < eanWithoutChecksum.length(); i++) {
            int digit = Character.getNumericValue(eanWithoutChecksum.charAt(i));

            // Multiplier les chiffres alternés par 3 et les autres par 1
            if (i % 2 == 0) {
                sum += digit; // Poids 1
            } else {
                sum += digit * 3; // Poids 3
            }
        }

        // Trouver le chiffre qui rend la somme multiple de 10
        int modulo = sum % 10;
        return (modulo == 0) ? 0 : 10 - modulo;
    }

    public  String generateEAN13() {
        Random random = new Random();

        // Générer les 12 premiers chiffres aléatoires
        StringBuilder eanBuilder = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            eanBuilder.append(random.nextInt(10)); // Ajouter un chiffre aléatoire (0-9)
        }

        // Calculer le chiffre de contrôle
        String eanWithoutChecksum = eanBuilder.toString();
        int checksum = calculateChecksum(eanWithoutChecksum);

        // Ajouter le chiffre de contrôle à la fin
        eanBuilder.append(checksum);

        return eanBuilder.toString();
    }

    public  String generateEAN13WithFirstThreeChars(String firstThreeChars) {
        if (firstThreeChars.length() != 3 || !firstThreeChars.matches("\\d{3}")) {
            throw new IllegalArgumentException("Les trois premiers caractères doivent être exactement trois chiffres.");
        }

        Random random = new Random();

        // Commencer avec les trois premiers caractères définis
        StringBuilder eanBuilder = new StringBuilder();
        eanBuilder.append(firstThreeChars);

        // Générer les 9 chiffres restants aléatoirement
        for (int i = 0; i < 9; i++) {
            eanBuilder.append(random.nextInt(10)); // Ajouter un chiffre aléatoire (0-9)
        }

        // Calculer le chiffre de contrôle
        String eanWithoutChecksum = eanBuilder.toString();
        int checksum = calculateChecksum(eanWithoutChecksum);

        // Ajouter le chiffre de contrôle à la fin
        eanBuilder.append(checksum);

        return eanBuilder.toString();
    }


}
