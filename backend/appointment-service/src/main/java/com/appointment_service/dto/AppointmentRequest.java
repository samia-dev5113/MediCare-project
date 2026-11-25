package com.appointment_service.dto;

import com.appointment_service.enums.MotifConsultation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
public record AppointmentRequest(
        @NotNull(message = "La date est obligatoire")
        LocalDate date,
        @NotNull(message = "Le motif est obligatoire")
        MotifConsultation motif,
        @NotBlank(message = "Le médecin est obligatoire")
        @Size(max = 100, message = "Le nom du médecin ne doit pas dépasser 100 caractères")
        String medecin,
        @NotNull(message = "Le patient est obligatoire")
        Long patientId,
        @Size(max = 2000, message = "Les remarques ne doivent pas dépasser 2000 caractères")
        String remarques
) {
}

