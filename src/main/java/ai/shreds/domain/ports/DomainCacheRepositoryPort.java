package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import java.util.UUID;

public interface DomainCacheRepositoryPort {
    /**
     * Save a schedule entity to the cache.
     * @param schedule the schedule entity to be cached
     */
    void saveToCache(DomainScheduleEntity schedule);

    /**
     * Delete a schedule entity from the cache by its UUID.
     * @param scheduleId the UUID of the schedule to be removed from the cache
     */
    void deleteFromCache(UUID scheduleId);

    /**
     * Find a schedule entity in the cache by its UUID.
     * @param scheduleId the UUID of the schedule to be retrieved from the cache
     * @return the schedule entity if found, otherwise null
     */
    DomainScheduleEntity findInCache(UUID scheduleId);
}
