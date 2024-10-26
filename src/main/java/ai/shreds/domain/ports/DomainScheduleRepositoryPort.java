package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import java.util.UUID;

public interface DomainScheduleRepositoryPort {

    /**
     * Saves the provided Schedule entity to the data store.
     * This method is used for both creating new schedules and updating existing ones.
     *
     * @param schedule the schedule entity to save
     */
    void save(DomainScheduleEntity schedule);

    /**
     * Retrieves a Schedule entity by its unique identifier.
     *
     * @param scheduleId the UUID of the schedule to retrieve
     * @return the schedule entity if found, or null if not found
     */
    DomainScheduleEntity findById(UUID scheduleId);

    /**
     * Deletes the Schedule entity with the given unique identifier from the data store.
     *
     * @param scheduleId the UUID of the schedule to delete
     */
    void deleteById(UUID scheduleId);

    /**
     * Checks whether a Schedule entity exists with the given unique identifier.
     *
     * @param scheduleId the UUID of the schedule to check
     * @return true if the schedule exists, false otherwise
     */
    boolean existsById(UUID scheduleId);
}