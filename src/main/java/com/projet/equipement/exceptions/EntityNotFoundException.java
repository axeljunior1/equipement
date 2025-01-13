package com.projet.equipement.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " with ID " + id + " not found");
    }
    public EntityNotFoundException(String entityName, String ids) {
        super(entityName + " with ID " + ids + " not found");
    }}