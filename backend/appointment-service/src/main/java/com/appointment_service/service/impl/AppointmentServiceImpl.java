package com.appointment_service.service.impl;

import com.appointment_service.client.PatientRestClient;
import com.appointment_service.dto.AppointmentRequest;
import com.appointment_service.dto.AppointmentResponse;
import com.appointment_service.dto.PatientDto;
import com.appointment_service.dto.RemarquesRequest;
import com.appointment_service.exception.ResourceNotFoundException;
import com.appointment_service.mapper.AppointmentMapper;
import com.appointment_service.model.Appointment;
import com.appointment_service.repository.AppointmentRepository;
import com.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRestClient patientRestClient;

    @Override
    public List<AppointmentResponse> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::toEnrichedResponse)
                .toList();
    }

    @Override
    public AppointmentResponse findById(Long id) {
        return toEnrichedResponse(getOrThrow(id));
    }

    @Override
    public AppointmentResponse create(AppointmentRequest request) {
        Appointment saved = appointmentRepository.save(appointmentMapper.toEntity(request));
        return toEnrichedResponse(saved);
    }

    @Override
    public AppointmentResponse updateRemarques(Long id, RemarquesRequest request) {
        Appointment appointment = getOrThrow(id);
        appointment.setRemarques(request.remarques());
        return toEnrichedResponse(appointmentRepository.save(appointment));
    }

    @Override
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rendez-vous", id);
        }
        appointmentRepository.deleteById(id);
    }

    private Appointment getOrThrow(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous", id));
    }

    private AppointmentResponse toEnrichedResponse(Appointment appointment) {
        AppointmentResponse base = appointmentMapper.toResponse(appointment);
        PatientDto patient = fetchPatient(appointment.getPatientId());
        return new AppointmentResponse(base.id(), base.date(), base.motif(), base.medecin(),
                base.patientId(), base.remarques(), patient);
    }

    private PatientDto fetchPatient(Long patientId) {
        return patientRestClient.getPatientById(patientId);
    }
}
