package com.appointment_service.service;

import com.appointment_service.dto.CertificatRequest;
import com.appointment_service.dto.CertificatResponse;
import java.util.List;
public interface CertificatService {
    List<CertificatResponse> findByAppointment(Long appointmentId);
    CertificatResponse create(CertificatRequest request);
}
