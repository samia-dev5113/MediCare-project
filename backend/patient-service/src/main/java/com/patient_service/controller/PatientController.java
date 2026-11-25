package com.patient_service.controller;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponse;
import com.patient_service.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    @GetMapping
    public List<PatientResponse> getAllPatients() {
        return patientService.findAll();
    }
    @GetMapping("/{id}")
    public PatientResponse getPatientById(@PathVariable Long id) {
        return patientService.findById(id);
    }
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody PatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(request));
    }
    @PutMapping("/{id}")
    public PatientResponse updatePatient(@PathVariable Long id,
                                         @Valid @RequestBody PatientRequest request) {
        return patientService.update(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        patientService.delete(id);
    }
}
