package com.appointment_service.dto;

import com.appointment_service.enums.TypeCertificat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
public record CertificatRequest(
        @NotNull(message = "La consultation est obligatoire")
        Long appointmentId,
        @NotNull(message = "Le type de certificat est obligatoire")
        TypeCertificat typeCertificat,
        @NotBlank(message = "Le texte du certificat est obligatoire")
        @Size(max = 1000, message = "Le texte ne doit pas dépasser 1000 caractères")
        String texte,
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin
) {
}
