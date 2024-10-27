package ai.shreds.infrastructure.repositories.mappers;

import ai.shreds.domain.entities.DomainEntityMessageRequest;
import ai.shreds.infrastructure.repositories.entities.MessageRequestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageRequestMapper {

    DomainEntityMessageRequest toDomain(MessageRequestEntity entity);

    MessageRequestEntity toEntity(DomainEntityMessageRequest domain);
}