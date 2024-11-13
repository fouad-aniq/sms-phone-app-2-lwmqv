package ai.shreds.application.ports;

import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;

public interface ApplicationCreateScheduleInputPort {
    SharedScheduleDto createSchedule(SharedScheduleDto scheduleDto) throws DomainInvalidScheduleException;
}