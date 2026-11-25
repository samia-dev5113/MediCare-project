package com.appointment_service.service.impl;

import com.appointment_service.dto.CertificatRequest;
import com.appointment_service.dto.CertificatResponse;
import com.appointment_service.exception.BusinessRuleException;
import com.appointment_service.exception.ResourceNotFoundException;
import com.appointment_service.mapper.CertificatMapper;
import com.appointment_service.model.Certificat;
import com.appointment_service.repository.AppointmentRepository;
import com.appointment_service.repository.CertificatRepository;
import com.appointment_service.service.CertificatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CertificatServiceImpl implements CertificatService {
    private final CertificatRepository certificatRepository;
    private final AppointmentRepository appointmentRepository;
    private final CertificatMapper certificatMapper;
    @Override
    @Transactional(readOnly = true)
    public List<CertificatResponse> findByAppointment(Long appointmentId) {
        return certificatMapper.toResponseList(
                certificatRepository.findByAppointmentId(appointmentId));
    }
    @Override
    @Transactional
    public CertificatResponse create(CertificatRequest request) {
        if (!appointmentRepository.existsById(request.appointmentId())) {
            throw new ResourceNotFoundException("Rendez-vous", request.appointmentId());
        }
        if (request.dateFin().isBefore(request.dateDebut())) {
            throw new BusinessRuleException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
        Certificat certificat = certificatMapper.toEntity(request);
        certificat.setDateEmission(LocalDate.now());
        return certificatMapper.toResponse(certificatRepository.save(certificat));
    }
}
