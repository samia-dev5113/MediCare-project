package com.appointment_service.mapper;

import com.appointment_service.dto.AppointmentRequest;
import com.appointment_service.dto.AppointmentResponse;
import com.appointment_service.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "patient", ignore = true)
    AppointmentResponse toResponse(Appointment appointment);
    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentRequest request);
}