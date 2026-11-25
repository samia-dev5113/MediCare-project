package com.appointment_service.mapper;

import com.appointment_service.dto.CertificatRequest;
import com.appointment_service.dto.CertificatResponse;
import com.appointment_service.model.Certificat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
@Mapper(componentModel = "spring")
public interface CertificatMapper {
    CertificatResponse toResponse(Certificat certificat);
    List<CertificatResponse> toResponseList(List<Certificat> certificats);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateEmission", ignore = true)
    Certificat toEntity(CertificatRequest request);
}
