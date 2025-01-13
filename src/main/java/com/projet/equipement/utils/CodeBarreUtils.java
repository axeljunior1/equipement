package com.projet.equipement.utils;

public class CodeBarreUtils {

    // Méthode pour générer un code-barres EAN-13 valide avec un préfixe
    public static String generateEAN13WithPrefix(String prefix) {
        if (prefix == null || !(prefix.equals("1") || prefix.equals("9"))) {
            throw new IllegalArgumentException("Le préfixe doit être '1' pour produit ou '9' pour lot.");
        }

        // Générer les 12 premiers chiffres du code-barres (12 chiffres aléatoires après le préfixe)
        StringBuilder codeSansChecksum = new StringBuilder(prefix);

        // Générer 11 chiffres aléatoires (après le préfixe)
        for (int i = 0; i < 11; i++) {
            codeSansChecksum.append((int) (Math.random() * 10)); // Chiffre aléatoire entre 0 et 9
        }

        // Calculer le chiffre de contrôle (checksum)
        int checksum = calculateEAN13Checksum(codeSansChecksum.toString());

        // Ajouter le chiffre de contrôle à la fin pour obtenir un code-barres valide
        codeSansChecksum.append(checksum);

        return codeSansChecksum.toString();
    }

    // Méthode pour calculer le checksum d'un code-barres EAN-13 (dernière position)
    private static int calculateEAN13Checksum(String code) {
        if (code.length() != 12) {
            throw new IllegalArgumentException("Le code doit contenir 12 chiffres avant le checksum.");
        }

        int sum = 0;

        // Additionner les chiffres aux positions impaires et paires (1er, 3e, 5e, etc.)
        for (int i = 0; i < code.length(); i++) {
            int digit = Character.getNumericValue(code.charAt(i));

            if (i % 2 == 0) { // Impair
                sum += digit;
            } else { // Pair
                sum += digit * 3;
            }
        }

        // Calculer le chiffre de contrôle (checksum)
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }
}
