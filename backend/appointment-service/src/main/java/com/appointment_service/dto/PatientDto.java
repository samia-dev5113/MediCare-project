package com.appointment_service.dto;

import com.appointment_service.enums.GroupeSanguin;
public record PatientDto(
        Long id,
        String nom,
        String prenom,
        GroupeSanguin groupeSanguin
) {
}

