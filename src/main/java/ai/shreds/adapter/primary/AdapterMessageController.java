package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationInputPortMessageReceptionPort;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.adapter.exceptions.AdapterException;
import ai.shreds.adapter.exceptions.AdapterExceptionValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class AdapterMessageController {

    private final ApplicationInputPortMessageReceptionPort messageReceptionPort;

    public AdapterMessageController(ApplicationInputPortMessageReceptionPort messageReceptionPort) {
        this.messageReceptionPort = messageReceptionPort;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public SharedResponseDTO receiveMessage(@RequestBody SharedSMSMessageDTO messageDto) {
        return messageReceptionPort.receiveMessage(messageDto);
    }

    @ExceptionHandler(AdapterExceptionValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SharedResponseDTO handleValidationException(AdapterExceptionValidationException ex) {
        SharedResponseDTO response = SharedResponseDTO.error(null, "Validation Error", List.of(ex.getMessage()));
        return response;
    }

    @ExceptionHandler(AdapterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SharedResponseDTO handleAdapterException(AdapterException ex) {
        SharedResponseDTO response = SharedResponseDTO.error(null, "Processing Error", List.of("An internal processing error occurred."));
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SharedResponseDTO handleGeneralException(Exception ex) {
        SharedResponseDTO response = SharedResponseDTO.error(null, "Internal Server Error", List.of("An unexpected error occurred."));
        return response;
    }
}