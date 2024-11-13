package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationCacheSyncPort;
import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.ports.DomainCacheRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationCacheSyncService implements ApplicationCacheSyncPort {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationCacheSyncService.class);

    private final DomainCacheRepositoryPort cacheRepositoryPort;

    public ApplicationCacheSyncService(DomainCacheRepositoryPort cacheRepositoryPort) {
        this.cacheRepositoryPort = cacheRepositoryPort;
    }

    @Override
    public synchronized void syncScheduleToCache(DomainScheduleEntity schedule) {
        try {
            cacheRepositoryPort.saveToCache(schedule);
            logger.info("Schedule with ID {} synchronized to cache.", schedule.getScheduleId());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while synchronizing schedule to cache for scheduleId: {}", schedule.getScheduleId(), e);
        }
    }

    @Override
    public synchronized void removeScheduleFromCache(UUID scheduleId) {
        try {
            cacheRepositoryPort.deleteFromCache(scheduleId);
            logger.info("Schedule with ID {} removed from cache.", scheduleId);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while removing schedule from cache for scheduleId: {}", scheduleId, e);
        }
    }
}