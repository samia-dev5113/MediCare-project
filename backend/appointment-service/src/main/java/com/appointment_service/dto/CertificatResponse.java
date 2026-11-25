package com.appointment_service.dto;

import com.appointment_service.enums.TypeCertificat;
import java.time.LocalDate;
public record CertificatResponse(
        Long id,
        Long appointmentId,
        TypeCertificat typeCertificat,
        String texte,
        LocalDate dateEmission,
        LocalDate dateDebut,
        LocalDate dateFin
) {
}