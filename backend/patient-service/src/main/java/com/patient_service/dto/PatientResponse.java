package com.patient_service.dto;

import com.patient_service.enums.GroupeSanguin;
public record PatientResponse(
        Long id,
        String nom,
        String prenom,
        GroupeSanguin groupeSanguin,
        String telephone
) {
}

