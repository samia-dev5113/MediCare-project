package com.appointment_service.controller;

import com.appointment_service.dto.OrdonnanceRequest;
import com.appointment_service.dto.OrdonnanceResponse;
import com.appointment_service.service.OrdonnanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/ordonnances")
@RequiredArgsConstructor
public class OrdonnanceController {
    private final OrdonnanceService ordonnanceService;
    @GetMapping("/appointment/{appointmentId}")
    public List<OrdonnanceResponse> getByAppointment(@PathVariable Long appointmentId) {
        return ordonnanceService.findByAppointment(appointmentId);
    }
    @PostMapping
    public ResponseEntity<OrdonnanceResponse> create(
            @Valid @RequestBody OrdonnanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordonnanceService.create(request));
    }
}