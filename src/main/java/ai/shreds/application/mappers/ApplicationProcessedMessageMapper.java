package ai.shreds.application.mappers;

import ai.shreds.shared.SharedProcessedMessageDTO;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DeliveryDetailsMapper.class})
public interface ApplicationProcessedMessageMapper {

    ApplicationProcessedMessageMapper INSTANCE = Mappers.getMapper(ApplicationProcessedMessageMapper.class);

    DomainEntityProcessedMessage toDomain(SharedProcessedMessageDTO messageDTO);

    SharedProcessedMessageDTO toDTO(DomainEntityProcessedMessage domainMessage);

}