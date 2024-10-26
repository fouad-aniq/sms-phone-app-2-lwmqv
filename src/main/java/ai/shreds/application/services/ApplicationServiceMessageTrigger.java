package ai.shreds.application.services;

import ai.shreds.application.ports.ApplicationOutputPortMessageTrigger;
import ai.shreds.domain.services.DomainServiceMessageTrigger;
import ai.shreds.application.services.ApplicationServiceErrorHandling;
import ai.shreds.shared.SharedScheduleDTO;
import ai.shreds.domain.entities.DomainEntitySchedule;
import ai.shreds.shared.SharedValueScheduleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.sql.Timestamp;

@Service
public class ApplicationServiceMessageTrigger implements ApplicationOutputPortMessageTrigger {

    private final DomainServiceMessageTrigger domainServiceMessageTrigger;
    private final ApplicationServiceErrorHandling applicationServiceErrorHandling;

    @Autowired
    public ApplicationServiceMessageTrigger(DomainServiceMessageTrigger domainServiceMessageTrigger,
                                            ApplicationServiceErrorHandling applicationServiceErrorHandling) {
        this.domainServiceMessageTrigger = domainServiceMessageTrigger;
        this.applicationServiceErrorHandling = applicationServiceErrorHandling;
    }

    @Override
    public void triggerMessage(SharedScheduleDTO schedule) {
        try {
            DomainEntitySchedule domainSchedule = mapToDomainEntitySchedule(schedule);
            domainServiceMessageTrigger.triggerMessage(domainSchedule);
        } catch (Exception exception) {
            applicationServiceErrorHandling.handleError(exception, schedule);
        }
    }

    private DomainEntitySchedule mapToDomainEntitySchedule(SharedScheduleDTO scheduleDTO) {
        DomainEntitySchedule domainSchedule = new DomainEntitySchedule();
        domainSchedule.setScheduleId(scheduleDTO.getScheduleId());
        domainSchedule.setMessageContent(scheduleDTO.getMessageContent());
        domainSchedule.setRecipient(scheduleDTO.getRecipient());
        domainSchedule.setScheduledTime(scheduleDTO.getScheduledTime());
        domainSchedule.setStatus(SharedValueScheduleStatus.valueOf(scheduleDTO.getStatus()));
        domainSchedule.setCreatedAt(scheduleDTO.getCreatedAt());
        domainSchedule.setUpdatedAt(scheduleDTO.getUpdatedAt());
        return domainSchedule;
    }
}