package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationInputPortMessageReceptionPort;
import ai.shreds.shared.SharedSMSMessageDTO;
import ai.shreds.shared.SharedResponseDTO;
import ai.shreds.adapter.exceptions.AdapterException;
import ai.shreds.adapter.exceptions.AdapterExceptionValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class AdapterMessageController {

    @Autowired
    private ApplicationInputPortMessageReceptionPort messageReceptionPort;

    @PostMapping
    public SharedResponseDTO receiveMessage(@RequestBody SharedSMSMessageDTO messageDto) throws AdapterException, AdapterExceptionValidationException {
        try {
            return messageReceptionPort.receiveMessage(messageDto);
        } catch (AdapterExceptionValidationException e) {
            SharedResponseDTO errorResponse = new SharedResponseDTO();
            errorResponse.setStatus("FAILED");
            errorResponse.setMessage(e.getMessage());
            errorResponse.setErrors(List.of(e.getMessage()));
            return errorResponse;
        } catch (AdapterException e) {
            SharedResponseDTO errorResponse = new SharedResponseDTO();
            errorResponse.setStatus("FAILED");
            errorResponse.setMessage("Internal Server Error");
            return errorResponse;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SharedResponseDTO> handleGeneralException(Exception e) {
        SharedResponseDTO errorResponse = new SharedResponseDTO();
        errorResponse.setStatus("FAILED");
        errorResponse.setMessage("Unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
