package com.appointment_service.mapper;

import com.appointment_service.dto.OrdonnanceRequest;
import com.appointment_service.dto.OrdonnanceResponse;
import com.appointment_service.model.Ordonnance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
@Mapper(componentModel = "spring")
public interface OrdonnanceMapper {
    OrdonnanceResponse toResponse(Ordonnance ordonnance);
    List<OrdonnanceResponse> toResponseList(List<Ordonnance> ordonnances);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateEmission", ignore = true)
    Ordonnance toEntity(OrdonnanceRequest request);
}
