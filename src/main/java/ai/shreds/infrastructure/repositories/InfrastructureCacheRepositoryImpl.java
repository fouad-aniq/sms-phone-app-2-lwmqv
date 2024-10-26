package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.ports.DomainCacheRepositoryPort;
import ai.shreds.infrastructure.entities.InfrastructureCacheScheduleEntity;
import ai.shreds.infrastructure.mappers.InfrastructureScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InfrastructureCacheRepositoryImpl implements DomainCacheRepositoryPort {

    @Autowired
    private RedisTemplate<String, InfrastructureCacheScheduleEntity> redisTemplate;

    @Autowired
    private InfrastructureScheduleMapper infrastructureScheduleMapper;

    @Override
    public void saveToCache(DomainScheduleEntity schedule) {
        try {
            InfrastructureCacheScheduleEntity cacheEntity = infrastructureScheduleMapper.toCacheEntity(schedule);
            redisTemplate.opsForValue().set(schedule.getScheduleId().toString(), cacheEntity);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            System.err.println("Failed to save schedule to cache: " + e.getMessage());
        }
    }

    @Override
    public void deleteFromCache(UUID scheduleId) {
        try {
            redisTemplate.delete(scheduleId.toString());
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            System.err.println("Failed to delete schedule from cache: " + e.getMessage());
        }
    }

    @Override
    public DomainScheduleEntity findInCache(UUID scheduleId) {
        try {
            InfrastructureCacheScheduleEntity cacheEntity = redisTemplate.opsForValue().get(scheduleId.toString());
            if (cacheEntity != null) {
                return infrastructureScheduleMapper.toDomainEntityFromCache(cacheEntity);
            }
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            System.err.println("Failed to find schedule in cache: " + e.getMessage());
        }
        return null;
    }
}