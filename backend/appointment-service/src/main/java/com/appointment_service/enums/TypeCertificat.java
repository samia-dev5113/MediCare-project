package com.appointment_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum TypeCertificat {
    DISPENSE("Dispense"),
    ARRET_TRAVAIL("Arrêt de travail"),
    APTITUDE("Certificat d'aptitude");
    private final String libelle;
}
