package ai.shreds.infrastructure.mongodb.mappers;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.infrastructure.mongodb.entities.ProcessedMessageMongoEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between DomainEntityProcessedMessage and ProcessedMessageMongoEntity.
 */
@Mapper(componentModel = "spring")
public interface ProcessedMessageEntityMapper {

    /**
     * Converts a DomainEntityProcessedMessage to a ProcessedMessageMongoEntity.
     *
     * @param domainEntity the domain entity to convert
     * @return the corresponding MongoDB entity
     */
    ProcessedMessageMongoEntity toMongoEntity(DomainEntityProcessedMessage domainEntity);

    /**
     * Converts a ProcessedMessageMongoEntity to a DomainEntityProcessedMessage.
     *
     * @param mongoEntity the MongoDB entity to convert
     * @return the corresponding domain entity
     */
    DomainEntityProcessedMessage toDomainEntity(ProcessedMessageMongoEntity mongoEntity);
}