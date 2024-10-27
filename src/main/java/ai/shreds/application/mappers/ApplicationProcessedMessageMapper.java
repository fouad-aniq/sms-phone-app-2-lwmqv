package ai.shreds.application.mappers;

import ai.shreds.shared.SharedDeliveryAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;
import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDeliveryAcknowledgment;
import ai.shreds.domain.value_objects.DomainValueErrorNotification;
import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApplicationProcessedMessageMapper {

    ApplicationProcessedMessageMapper INSTANCE = Mappers.getMapper(ApplicationProcessedMessageMapper.class);

    @Mapping(source = "dispatchStatus.status", target = "deliveryStatus")
    DomainValueDeliveryAcknowledgment toDomainDeliveryAcknowledgment(SharedDeliveryAcknowledgmentDTO acknowledgmentDTO);

    DomainValueErrorNotification toDomainErrorNotification(SharedErrorNotificationDTO errorNotificationDTO);

    @Mapping(source = "dispatchStatus", target = "dispatchStatus")
    @Mapping(source = "deliveryDetails", target = "deliveryDetails")
    DomainEntityProcessedMessage toDomain(SharedProcessedMessageDTO messageDTO);
}