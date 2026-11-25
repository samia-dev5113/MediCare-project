package com.appointment_service.dto;

import jakarta.validation.constraints.Size;
public record RemarquesRequest(
        @Size(max = 2000, message = "Les remarques ne doivent pas dépasser 2000 caractères")
        String remarques
) {
}

