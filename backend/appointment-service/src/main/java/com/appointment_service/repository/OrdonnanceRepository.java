package com.appointment_service.repository;

import com.appointment_service.model.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {
    List<Ordonnance> findByAppointmentId(Long appointmentId);
}

