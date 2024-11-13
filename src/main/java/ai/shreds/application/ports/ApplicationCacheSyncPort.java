package ai.shreds.application.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import java.util.UUID;

/**
 * The ApplicationCacheSyncPort interface defines methods for synchronizing schedule data with the cache
 * system to ensure consistency between the database and the cache.
 */
public interface ApplicationCacheSyncPort {

    /**
     * Synchronizes the given schedule entity to the cache immediately after it's created or updated in the database.
     * This method ensures that the cache reflects the latest state of the schedule.
     * 
     * @param schedule the DomainScheduleEntity object representing the schedule to be cached
     */
    void syncScheduleToCache(DomainScheduleEntity schedule);

    /**
     * Removes the schedule from the cache immediately after it's deleted from the database.
     * This prevents stale data from being served from the cache.
     * 
     * @param scheduleId the UUID of the schedule to be removed from the cache
     */
    void removeScheduleFromCache(UUID scheduleId);
}
