package com.appointment_service.model;

import com.appointment_service.enums.TypeCertificat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long appointmentId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeCertificat typeCertificat;
    @Column(length = 1000)
    private String texte;
    private LocalDate dateEmission;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
