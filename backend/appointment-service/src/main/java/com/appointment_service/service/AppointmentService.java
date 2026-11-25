package com.appointment_service.service;

import com.appointment_service.dto.AppointmentRequest;
import com.appointment_service.dto.AppointmentResponse;
import com.appointment_service.dto.RemarquesRequest;
import java.util.List;
public interface AppointmentService {
    List<AppointmentResponse> findAll();
    AppointmentResponse findById(Long id);
    AppointmentResponse create(AppointmentRequest request);
    AppointmentResponse updateRemarques(Long id, RemarquesRequest request);
    void delete(Long id);
}
