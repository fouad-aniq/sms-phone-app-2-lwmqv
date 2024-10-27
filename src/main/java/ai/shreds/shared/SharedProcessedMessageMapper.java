package ai.shreds.shared;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.shared.SharedDeliveryDetailsMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {SharedDeliveryDetailsMapper.class})
public interface SharedProcessedMessageMapper {

    DomainEntityProcessedMessage toDomain(SharedProcessedMessageDTO messageDTO);

    @Mapping(target = "deliveryDetails", ignore = true)
    SharedProcessedMessageDTO toDTO(DomainEntityProcessedMessage messageEntity);

}