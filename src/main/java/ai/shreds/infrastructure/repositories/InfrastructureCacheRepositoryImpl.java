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

    private final RedisTemplate<String, InfrastructureCacheScheduleEntity> redisTemplate;
    private final InfrastructureScheduleMapper scheduleMapper;

    @Autowired
    public InfrastructureCacheRepositoryImpl(RedisTemplate<String, InfrastructureCacheScheduleEntity> redisTemplate,
                                             InfrastructureScheduleMapper scheduleMapper) {
        this.redisTemplate = redisTemplate;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public void saveToCache(DomainScheduleEntity schedule) {
        try {
            InfrastructureCacheScheduleEntity cacheEntity = scheduleMapper.toCacheEntity(schedule);
            String key = schedule.getScheduleId().toString();
            redisTemplate.opsForValue().set(key, cacheEntity);
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to save schedule to cache", e);
        }
    }

    @Override
    public void deleteFromCache(UUID scheduleId) {
        try {
            String key = scheduleId.toString();
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to delete schedule from cache", e);
        }
    }

    @Override
    public DomainScheduleEntity findInCache(UUID scheduleId) {
        try {
            String key = scheduleId.toString();
            InfrastructureCacheScheduleEntity cacheEntity = redisTemplate.opsForValue().get(key);
            if (cacheEntity == null) {
                return null;
            }
            return scheduleMapper.toDomainEntityFromCache(cacheEntity);
        } catch (Exception e) {
            throw new InfrastructureCacheAccessException("Failed to retrieve schedule from cache", e);
        }
    }
}
