package com.patient_service.mapper;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponse;
import com.patient_service.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientResponse toResponse(Patient patient);
    List<PatientResponse> toResponseList(List<Patient> patients);
    @Mapping(target = "id", ignore = true)
    Patient toEntity(PatientRequest request);
    @Mapping(target = "id", ignore = true)
    void updateEntity(PatientRequest request, @MappingTarget Patient patient);
}

