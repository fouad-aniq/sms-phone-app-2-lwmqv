package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationInputPortScheduleUpdateListener;
import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.domain.services.DomainServiceScheduleUpdateListener;
import ai.shreds.shared.SharedScheduleAddedEvent;
import ai.shreds.shared.SharedScheduleRemovedEvent;
import ai.shreds.shared.SharedScheduleUpdatedEvent;
import ai.shreds.infrastructure.repositories.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceScheduleUpdateListener implements ApplicationInputPortScheduleUpdateListener {

    private final DomainServiceScheduleUpdateListener domainServiceScheduleUpdateListener;
    private final ApplicationServiceErrorHandling errorHandlingService;
    private final ScheduleMapper scheduleMapper;

    @Override
    public void onScheduleAdded(SharedScheduleAddedEvent event) {
        try {
            DomainEntitySchedule schedule = scheduleMapper.toDomainEntitySchedule(event);
            domainServiceScheduleUpdateListener.onScheduleAdded(schedule);
        } catch (Exception e) {
            errorHandlingService.handleError(e, event);
        }
    }

    @Override
    public void onScheduleUpdated(SharedScheduleUpdatedEvent event) {
        try {
            DomainEntitySchedule schedule = scheduleMapper.toDomainEntitySchedule(event);
            domainServiceScheduleUpdateListener.onScheduleUpdated(schedule);
        } catch (Exception e) {
            errorHandlingService.handleError(e, event);
        }
    }

    @Override
    public void onScheduleRemoved(SharedScheduleRemovedEvent event) {
        try {
            domainServiceScheduleUpdateListener.onScheduleRemoved(event.getScheduleId());
        } catch (Exception e) {
            errorHandlingService.handleError(e, event);
        }
    }
}