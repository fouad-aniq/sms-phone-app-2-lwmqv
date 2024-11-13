package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationCreateScheduleInputPort;
import ai.shreds.application.ports.ApplicationDeleteScheduleInputPort;
import ai.shreds.application.ports.ApplicationUpdateScheduleInputPort;
import ai.shreds.application.ports.ApplicationValidateScheduleInputPort;
import ai.shreds.shared.SharedScheduleDto;
import ai.shreds.shared.SharedScheduleResponse;
import ai.shreds.shared.SharedValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedules")
public class AdapterScheduleController {

    private final ApplicationCreateScheduleInputPort createScheduleService;
    private final ApplicationUpdateScheduleInputPort updateScheduleService;
    private final ApplicationDeleteScheduleInputPort deleteScheduleService;
    private final ApplicationValidateScheduleInputPort validateScheduleService;

    @Autowired
    public AdapterScheduleController(ApplicationCreateScheduleInputPort createScheduleService,
                                     ApplicationUpdateScheduleInputPort updateScheduleService,
                                     ApplicationDeleteScheduleInputPort deleteScheduleService,
                                     ApplicationValidateScheduleInputPort validateScheduleService) {
        this.createScheduleService = createScheduleService;
        this.updateScheduleService = updateScheduleService;
        this.deleteScheduleService = deleteScheduleService;
        this.validateScheduleService = validateScheduleService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SharedScheduleDto createSchedule(@Valid @RequestBody SharedScheduleDto scheduleDto) {
        return createScheduleService.createSchedule(scheduleDto);
    }

    @PutMapping("/{scheduleId}")
    public SharedScheduleDto modifySchedule(@PathVariable("scheduleId") UUID scheduleId,
                                            @Valid @RequestBody SharedScheduleDto scheduleDto) {
        return updateScheduleService.updateSchedule(scheduleId, scheduleDto);
    }

    @DeleteMapping("/{scheduleId}")
    public SharedScheduleResponse deleteSchedule(@PathVariable("scheduleId") UUID scheduleId) {
        return deleteScheduleService.deleteSchedule(scheduleId);
    }

    @PostMapping("/validate")
    public SharedValidationResponse validateScheduleData(@Valid @RequestBody SharedScheduleDto scheduleDto) {
        return validateScheduleService.validateScheduleData(scheduleDto);
    }
}
