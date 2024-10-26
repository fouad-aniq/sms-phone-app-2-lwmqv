package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainScheduleEntity;
import java.util.UUID;

/**
 * DomainCacheRepositoryPort provides an abstraction for cache operations
 * related to Schedule entities within the domain layer.
 * It allows saving, deleting, and retrieving schedules from the cache
 * without exposing the underlying cache implementation.
 */
public interface DomainCacheRepositoryPort {

    /**
     * Saves a schedule entity to the cache.
     * 
     * @param schedule the schedule entity to be saved to the cache.
     */
    void saveToCache(DomainScheduleEntity schedule);

    /**
     * Deletes a schedule from the cache using its unique identifier.
     * 
     * @param scheduleId the UUID of the schedule to be deleted from the cache.
     */
    void deleteFromCache(UUID scheduleId);

    /**
     * Retrieves a schedule entity from the cache using its unique identifier.
     * 
     * @param scheduleId the UUID of the schedule to retrieve from the cache.
     * @return the schedule entity if found in the cache, or null if not found.
     */
    DomainScheduleEntity findInCache(UUID scheduleId);
}