package com.patient_service.service;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponse;
import java.util.List;
public interface PatientService {
    List<PatientResponse> findAll();
    PatientResponse findById(Long id);
    PatientResponse create(PatientRequest request);
    PatientResponse update(Long id, PatientRequest request);
    void delete(Long id);
}

