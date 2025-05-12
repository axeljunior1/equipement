package com.projet.equipement.exceptions;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String tenantName) {
        super("La resource " + tenantName + " avec l'identifiant  n'a pas été trouvée");
    }

}
