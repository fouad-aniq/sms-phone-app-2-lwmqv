package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainEntityDeliveryDetails;

public interface DomainPortDeliveryDetailsRepository {

    /**
     * Saves the delivery details to the repository.
     * @param deliveryDetails the delivery details to save
     */
    void save(DomainEntityDeliveryDetails deliveryDetails);

    /**
     * Finds the delivery details by the message ID.
     * @param messageId the ID of the message
     * @return the delivery details associated with the message ID
     */
    DomainEntityDeliveryDetails findByMessageId(String messageId);

}