package ai.shreds.application.ports;

import java.util.UUID;
import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.domain.exceptions.DomainInvalidScheduleException;

public interface ApplicationUpdateScheduleInputPort {
    SharedScheduleDto updateSchedule(UUID scheduleId, SharedScheduleDto scheduleDto) throws DomainInvalidScheduleException;
}