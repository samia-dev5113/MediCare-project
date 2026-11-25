package com.appointment_service.client;

import com.appointment_service.dto.PatientDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "patient-service")
public interface PatientRestClient {
    Logger LOG = LoggerFactory.getLogger(PatientRestClient.class);
    @GetMapping("/patients/{id}")
    @CircuitBreaker(name = "patientService", fallbackMethod = "getDefaultPatient")
    PatientDto getPatientById(@PathVariable("id") Long id);
    default PatientDto getDefaultPatient(Long id, Exception exception) {
        LOG.warn("Fallback patient {} : {}", id, exception.getClass().getSimpleName());
        return new PatientDto(id, "Non disponible", "Non disponible", null);
    }
}
