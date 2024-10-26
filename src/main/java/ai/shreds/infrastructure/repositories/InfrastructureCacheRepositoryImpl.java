package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainScheduleEntity;
import ai.shreds.domain.ports.DomainCacheRepositoryPort;
import ai.shreds.infrastructure.entities.InfrastructureCacheScheduleEntity;
import ai.shreds.infrastructure.exceptions.InfrastructureCacheAccessException;
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
    public void saveToCache(DomainScheduleEntity schedule) throws InfrastructureCacheAccessException {
        try {
            InfrastructureCacheScheduleEntity cacheEntity = infrastructureScheduleMapper.toCacheEntity(schedule);
            redisTemplate.opsForValue().set(schedule.getScheduleId().toString(), cacheEntity);
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to save schedule to cache", e);
        }
    }

    @Override
    public void deleteFromCache(UUID scheduleId) throws InfrastructureCacheAccessException {
        try {
            redisTemplate.delete(scheduleId.toString());
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to delete schedule from cache", e);
        }
    }

    @Override
    public DomainScheduleEntity findInCache(UUID scheduleId) throws InfrastructureCacheAccessException {
        try {
            InfrastructureCacheScheduleEntity cacheEntity = redisTemplate.opsForValue().get(scheduleId.toString());
            if (cacheEntity != null) {
                return infrastructureScheduleMapper.toDomainEntityFromCache(cacheEntity);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to find schedule in cache", e);
        }
    }
}