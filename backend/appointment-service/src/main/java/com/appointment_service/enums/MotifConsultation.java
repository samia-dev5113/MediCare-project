package com.appointment_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum MotifConsultation {
    CONSULTATION_GENERALE("Consultation générale"),
    SUIVI("Suivi médical"),
    URGENCE("Urgence"),
    VACCINATION("Vaccination"),
    RESULTATS_ANALYSES("Résultats d'analyses"),
    CERTIFICAT("Demande de certificat");
    private final String libelle;
}
