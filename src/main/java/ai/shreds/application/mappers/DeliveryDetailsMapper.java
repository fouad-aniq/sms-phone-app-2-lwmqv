package ai.shreds.application.mappers;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.infrastructure.entities.DeliveryDetailsEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between DomainEntityDeliveryDetails and DeliveryDetailsEntity.
 */
@Mapper(componentModel = "spring")
public interface DeliveryDetailsMapper {

    /**
     * Converts a DomainEntityDeliveryDetails object to a DeliveryDetailsEntity object.
     * @param domain the domain object to convert
     * @return the converted entity object
     */
    DeliveryDetailsEntity toEntity(DomainEntityDeliveryDetails domain);

    /**
     * Converts a DeliveryDetailsEntity object to a DomainEntityDeliveryDetails object.
     * @param entity the entity object to convert
     * @return the converted domain object
     */
    DomainEntityDeliveryDetails toDomain(DeliveryDetailsEntity entity);
}