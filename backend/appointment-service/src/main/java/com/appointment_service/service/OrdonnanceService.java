package com.appointment_service.service;

import com.appointment_service.dto.OrdonnanceRequest;
import com.appointment_service.dto.OrdonnanceResponse;
import java.util.List;
public interface OrdonnanceService {
    List<OrdonnanceResponse> findByAppointment(Long appointmentId);
    OrdonnanceResponse create(OrdonnanceRequest request);
}
