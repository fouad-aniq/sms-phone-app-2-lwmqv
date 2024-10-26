package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.exceptions.DomainExceptionScheduleNotFound;
import ai.shreds.domain.ports.DomainPortScheduleCache;
import ai.shreds.shared.SharedValueScheduleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Repository
public class InfrastructureRepositoryRedisScheduleCacheImpl implements DomainPortScheduleCache {

    private static final String SCHEDULE_KEY_PREFIX = "schedule:";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    public InfrastructureRepositoryRedisScheduleCacheImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String getScheduleKey(UUID scheduleId) {
        return SCHEDULE_KEY_PREFIX + scheduleId.toString();
    }

    @Override
    public List<DomainEntitySchedule> getAllSchedules() {
        List<DomainEntitySchedule> schedules = new ArrayList<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match(SCHEDULE_KEY_PREFIX + "*").count(1000).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);
        try {
            while (cursor.hasNext()) {
                String key = redisTemplate.getStringSerializer().deserialize(cursor.next());
                Map<String, Object> entries = hashOperations.entries(key);
                DomainEntitySchedule schedule = mapToSchedule(entries);
                if (schedule != null) {
                    schedules.add(schedule);
                }
            }
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    log.error("Error closing Redis cursor: {}", e.getMessage(), e);
                }
            }
        }
        return schedules;
    }

    @Override
    public List<DomainEntitySchedule> getSchedulesDueBy(Timestamp time) {
        List<DomainEntitySchedule> allSchedules = getAllSchedules();
        List<DomainEntitySchedule> dueSchedules = new ArrayList<>();
        for (DomainEntitySchedule schedule : allSchedules) {
            if (schedule.getScheduledTime().compareTo(time) <= 0 && schedule.getStatus() == SharedValueScheduleStatus.ACTIVE) {
                dueSchedules.add(schedule);
            }
        }
        return dueSchedules;
    }

    @Override
    public DomainEntitySchedule getScheduleById(UUID scheduleId) {
        String key = getScheduleKey(scheduleId);
        Map<String, Object> entries = hashOperations.entries(key);
        if (entries == null || entries.isEmpty()) {
            throw new DomainExceptionScheduleNotFound("Schedule not found with ID: " + scheduleId);
        }
        return mapToSchedule(entries);
    }

    @Override
    public void saveSchedule(DomainEntitySchedule schedule) {
        String key = getScheduleKey(schedule.getScheduleId());
        Map<String, Object> map = mapFromSchedule(schedule);
        hashOperations.putAll(key, map);
    }

    @Override
    public void deleteSchedule(UUID scheduleId) {
        String key = getScheduleKey(scheduleId);
        redisTemplate.delete(key);
    }

    private DomainEntitySchedule mapToSchedule(Map<String, Object> entries) {
        if (entries == null || entries.isEmpty()) {
            return null;
        }
        UUID scheduleId = UUID.fromString((String) entries.get("scheduleId"));
        String messageContent = (String) entries.get("messageContent");
        String recipient = (String) entries.get("recipient");
        Timestamp scheduledTime = new Timestamp(Long.parseLong((String) entries.get("scheduledTime")));
        SharedValueScheduleStatus status = SharedValueScheduleStatus.valueOf((String) entries.get("status"));
        Timestamp createdAt = new Timestamp(Long.parseLong((String) entries.get("createdAt")));
        Timestamp updatedAt = new Timestamp(Long.parseLong((String) entries.get("updatedAt")));

        DomainEntitySchedule schedule = new DomainEntitySchedule();
        schedule.setScheduleId(scheduleId);
        schedule.setMessageContent(messageContent);
        schedule.setRecipient(recipient);
        schedule.setScheduledTime(scheduledTime);
        schedule.setStatus(status);
        schedule.setCreatedAt(createdAt);
        schedule.setUpdatedAt(updatedAt);

        return schedule;
    }

    private Map<String, Object> mapFromSchedule(DomainEntitySchedule schedule) {
        Map<String, Object> map = new HashMap<>();
        map.put("scheduleId", schedule.getScheduleId().toString());
        map.put("messageContent", schedule.getMessageContent());
        map.put("recipient", schedule.getRecipient());
        map.put("scheduledTime", String.valueOf(schedule.getScheduledTime().getTime()));
        map.put("status", schedule.getStatus().name());
        map.put("createdAt", String.valueOf(schedule.getCreatedAt().getTime()));
        map.put("updatedAt", String.valueOf(schedule.getUpdatedAt().getTime()));
        return map;
    }
}