package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationCreateScheduleInputPort;
import ai.shreds.application.ports.ApplicationUpdateScheduleInputPort;
import ai.shreds.application.ports.ApplicationDeleteScheduleInputPort;
import ai.shreds.application.ports.ApplicationValidateScheduleInputPort;
import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedScheduleResponse;
import ai.shreds.shared.SharedValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
public class AdapterScheduleController {

    private final ApplicationCreateScheduleInputPort createScheduleInputPort;
    private final ApplicationUpdateScheduleInputPort updateScheduleInputPort;
    private final ApplicationDeleteScheduleInputPort deleteScheduleInputPort;
    private final ApplicationValidateScheduleInputPort validateScheduleInputPort;

    @Autowired
    public AdapterScheduleController(
            ApplicationCreateScheduleInputPort createScheduleInputPort,
            ApplicationUpdateScheduleInputPort updateScheduleInputPort,
            ApplicationDeleteScheduleInputPort deleteScheduleInputPort,
            ApplicationValidateScheduleInputPort validateScheduleInputPort) {
        this.createScheduleInputPort = createScheduleInputPort;
        this.updateScheduleInputPort = updateScheduleInputPort;
        this.deleteScheduleInputPort = deleteScheduleInputPort;
        this.validateScheduleInputPort = validateScheduleInputPort;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public SharedScheduleDto createSchedule(@RequestBody SharedScheduleDto scheduleDto) {
        return createScheduleInputPort.createSchedule(scheduleDto);
    }

    @PutMapping("/{scheduleId}")
    public SharedScheduleDto modifySchedule(@PathVariable UUID scheduleId, @RequestBody SharedScheduleDto scheduleDto) {
        return updateScheduleInputPort.updateSchedule(scheduleId, scheduleDto);
    }

    @DeleteMapping("/{scheduleId}")
    public SharedScheduleResponse deleteSchedule(@PathVariable UUID scheduleId) {
        return deleteScheduleInputPort.deleteSchedule(scheduleId);
    }

    @PostMapping("/validate")
    public SharedValidationResponse validateScheduleData(@RequestBody SharedScheduleDto scheduleDto) {
        return validateScheduleInputPort.validateScheduleData(scheduleDto);
    }
}