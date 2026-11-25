package com.appointment_service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super("%s introuvable avec l'identifiant %d".formatted(resource, id));
    }
}
