package com.projet.equipement.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super("La resource " + entityName + " avec l'identifiant " + id + " n'a pas été trouvée");
    }

    public EntityNotFoundException(String entityName, String identifiant) {
        super("La resource " + entityName + " avec l'identifiant " + identifiant + " n'a pas été trouvée");
    }
}
