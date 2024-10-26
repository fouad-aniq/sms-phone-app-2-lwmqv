package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.entities.DomainEntityMessageProcessingEvent;
import ai.shreds.domain.ports.DomainPortScheduleCache;
import ai.shreds.domain.ports.DomainPortMessageProducer;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.time.Instant;

public class DomainServiceScheduleMonitoring {

    private final DomainPortScheduleCache scheduleCache;
    private final DomainPortMessageProducer messageProducer;

    public DomainServiceScheduleMonitoring(DomainPortScheduleCache scheduleCache, DomainPortMessageProducer messageProducer) {
        this.scheduleCache = scheduleCache;
        this.messageProducer = messageProducer;
    }

    public List<DomainEntitySchedule> checkDueSchedules() {
        Timestamp currentTime = Timestamp.from(Instant.now());
        List<DomainEntitySchedule> dueSchedules = scheduleCache.getSchedulesDueBy(currentTime);
        for (DomainEntitySchedule schedule : dueSchedules) {
            processSchedule(schedule);
        }
        return dueSchedules;
    }

    public void processSchedule(DomainEntitySchedule schedule) {
        if ("active".equalsIgnoreCase(schedule.getStatus().getValue())) {
            DomainEntityMessageProcessingEvent event = new DomainEntityMessageProcessingEvent();
            event.setEventId(UUID.randomUUID());
            event.setScheduleId(schedule.getScheduleId());
            event.setMessageContent(schedule.getMessageContent());
            event.setRecipient(schedule.getRecipient());
            event.setTriggeredAt(Timestamp.from(Instant.now()));
            int maxRetries = 3;
            int attempt = 0;
            boolean success = false;
            while (!success && attempt < maxRetries) {
                try {
                    messageProducer.sendMessage(event);
                    success = true;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxRetries) {
                        throw new RuntimeException("Failed to send message after " + maxRetries + " attempts", e);
                    }
                    try {
                        Thread.sleep(1000); // Retry after 1 second
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}