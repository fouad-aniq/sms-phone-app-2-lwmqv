package ai.shreds.shared;

import org.mapstruct.Mapper;
import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.shared.SharedMessageRequestDTO;

@Mapper(componentModel = "spring")
public interface SharedMessageRequestMapper {

    DomainEntityMessageRequest toDomain(SharedMessageRequestDTO requestDTO);

    SharedMessageRequestDTO toDTO(DomainEntityMessageRequest requestEntity);
}