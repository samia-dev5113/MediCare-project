package com.appointment_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
public record OrdonnanceRequest(
        @NotNull(message = "La consultation est obligatoire")
        Long appointmentId,
        @NotEmpty(message = "Au moins un médicament est requis")
        List<@NotBlank(message = "Une ligne de médicament ne peut pas être vide")
        @Size(max = 500, message = "500 caractères maximum par ligne") String> medicaments
) {
}

