package ai.shreds.infrastructure.mapper;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.infrastructure.documents.DeliveryDetailsDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryDetailsMapper {

    DeliveryDetailsDocument toDocument(DomainEntityDeliveryDetails details);

    DomainEntityDeliveryDetails toDomain(DeliveryDetailsDocument document);
}