package com.patient_service.dto;

import com.patient_service.enums.GroupeSanguin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public record PatientRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
        String prenom,
        @NotNull(message = "Le groupe sanguin est obligatoire")
        GroupeSanguin groupeSanguin,
        @Pattern(regexp = "^\\+?[0-9 ]{8,20}$", message = "Numéro de téléphone invalide")
        String telephone
) {
}
