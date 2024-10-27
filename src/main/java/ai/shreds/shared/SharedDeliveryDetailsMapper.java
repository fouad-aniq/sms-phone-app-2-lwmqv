package ai.shreds.shared;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SharedDeliveryDetailsMapper {

    DomainEntityDeliveryDetails toDomain(SharedDeliveryDetailsDTO detailsDTO);

    SharedDeliveryDetailsDTO toDTO(DomainEntityDeliveryDetails detailsEntity);
}