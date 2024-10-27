package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDeliveryDetails;
import java.util.List;

public interface ApplicationOutputPortMessageRepositoryPort {
    void save(DomainEntityProcessedMessage message);

    DomainEntityProcessedMessage findById(String id);

    List<DomainEntityProcessedMessage> findByDispatchStatus(String status);

    void updateDispatchStatus(String id, String status);

    void incrementRetryCount(String id);

    void updateDeliveryDetails(String id, DomainValueDeliveryDetails details);
}