package com.appointment_service.dto;

import com.appointment_service.enums.MotifConsultation;
import java.time.LocalDate;
public record AppointmentResponse(
        Long id,
        LocalDate date,
        MotifConsultation motif,
        String medecin,
        Long patientId,
        String remarques,
        PatientDto patient
) {
}

