package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.exceptions.DomainScheduleNotFoundException;
import java.util.UUID;

public interface DomainScheduleRepositoryPort {

    /**
     * Saves a schedule entity to the database. This method should handle both creation and update operations.
     * Utilizes optimistic locking to handle high concurrency and prevent data conflicts during schedule persistence.
     * Manages database transactions appropriately to ensure data integrity and consistency.
     *
     * @param schedule the schedule entity to be saved
     */
    void save(DomainScheduleEntity schedule);

    /**
     * Retrieves a schedule entity by its UUID.
     * Throws a DomainScheduleNotFoundException if the schedule does not exist.
     * Handles and propagates database access exceptions consistently to prevent application crashes and provide meaningful error information to the service layer.
     *
     * @param scheduleId the UUID of the schedule entity
     * @return the found schedule entity
     */
    DomainScheduleEntity findById(UUID scheduleId);

    /**
     * Removes a schedule entity by its UUID from the database.
     * Throws a DomainScheduleNotFoundException if the schedule does not exist.
     * Manages transactions and exception propagation as per technical specifications.
     *
     * @param scheduleId the UUID of the schedule entity to be deleted
     */
    void deleteById(UUID scheduleId);

    /**
     * Checks if a schedule entity exists in the database by its UUID.
     *
     * @param scheduleId the UUID of the schedule entity
     * @return true if the schedule entity exists, false otherwise
     */
    boolean existsById(UUID scheduleId);
}
