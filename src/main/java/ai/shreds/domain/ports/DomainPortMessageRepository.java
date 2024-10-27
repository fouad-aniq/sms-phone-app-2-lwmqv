package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import java.util.List;

public interface DomainPortMessageRepository {

    void save(DomainEntityProcessedMessage message);

    DomainEntityProcessedMessage findById(String messageId);

    List<DomainEntityProcessedMessage> findByDispatchStatus(DomainValueDispatchStatus status);

    void deleteById(String messageId);

    Integer countByDispatchStatus(DomainValueDispatchStatus status);
}