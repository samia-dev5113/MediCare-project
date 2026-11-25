package com.appointment_service.service.impl;

import com.appointment_service.dto.OrdonnanceRequest;
import com.appointment_service.dto.OrdonnanceResponse;
import com.appointment_service.exception.ResourceNotFoundException;
import com.appointment_service.mapper.OrdonnanceMapper;
import com.appointment_service.model.Ordonnance;
import com.appointment_service.repository.AppointmentRepository;
import com.appointment_service.repository.OrdonnanceRepository;
import com.appointment_service.service.OrdonnanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrdonnanceServiceImpl implements OrdonnanceService {
    private final OrdonnanceRepository ordonnanceRepository;
    private final AppointmentRepository appointmentRepository;
    private final OrdonnanceMapper ordonnanceMapper;
    @Override
    @Transactional(readOnly = true)
    public List<OrdonnanceResponse> findByAppointment(Long appointmentId) {
        return ordonnanceMapper.toResponseList(
                ordonnanceRepository.findByAppointmentId(appointmentId));
    }
    @Override
    @Transactional
    public OrdonnanceResponse create(OrdonnanceRequest request) {
        if (!appointmentRepository.existsById(request.appointmentId())) {
            throw new ResourceNotFoundException("Rendez-vous", request.appointmentId());
        }
        Ordonnance ordonnance = ordonnanceMapper.toEntity(request);
        ordonnance.setDateEmission(LocalDate.now());
        return ordonnanceMapper.toResponse(ordonnanceRepository.save(ordonnance));
    }
}