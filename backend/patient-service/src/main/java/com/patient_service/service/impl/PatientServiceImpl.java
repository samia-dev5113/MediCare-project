package com.patient_service.service.impl;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponse;
import com.patient_service.exception.ResourceNotFoundException;
import com.patient_service.mapper.PatientMapper;
import com.patient_service.model.Patient;
import com.patient_service.repository.PatientRepository;
import com.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> findAll() {
        return patientMapper.toResponseList(patientRepository.findAll());
    }
    @Override
    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        return patientMapper.toResponse(getOrThrow(id));
    }
    @Override
    @Transactional
    public PatientResponse create(PatientRequest request) {
        Patient saved = patientRepository.save(patientMapper.toEntity(request));
        return patientMapper.toResponse(saved);
    }
    @Override
    @Transactional
    public PatientResponse update(Long id, PatientRequest request) {
        Patient existing = getOrThrow(id);
        patientMapper.updateEntity(request, existing);
        return patientMapper.toResponse(patientRepository.save(existing));
    }
    @Override
    @Transactional
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient", id);
        }
        patientRepository.deleteById(id);
    }
    private Patient getOrThrow(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }
}
