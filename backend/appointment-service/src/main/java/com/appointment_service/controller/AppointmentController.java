package com.appointment_service.controller;

import com.appointment_service.dto.AppointmentRequest;
import com.appointment_service.dto.AppointmentResponse;
import com.appointment_service.dto.RemarquesRequest;
import com.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    @GetMapping
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentService.findAll();
    }
    @GetMapping("/{id}")
    public AppointmentResponse getAppointment(@PathVariable Long id) {
        return appointmentService.findById(id);
    }
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.create(request));
    }
    @PatchMapping("/{id}/remarques")
    public AppointmentResponse updateRemarques(@PathVariable Long id,
                                               @Valid @RequestBody RemarquesRequest request) {
        return appointmentService.updateRemarques(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
    }
}
