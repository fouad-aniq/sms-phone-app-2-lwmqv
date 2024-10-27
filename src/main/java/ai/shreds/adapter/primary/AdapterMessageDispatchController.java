package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationInputPortMessageDispatchUseCase;
import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.shared.SharedDeliveryConfirmationDTO;
import ai.shreds.shared.SharedDeliveryConfirmationResponse;
import ai.shreds.shared.SharedEnumDeliveryStatus;
import ai.shreds.shared.SharedErrorDetails;
import ai.shreds.shared.SharedErrorNotificationDTO;
import ai.shreds.shared.SharedErrorNotificationResponse;
import ai.shreds.shared.SharedMessageDispatchRequestParams;
import ai.shreds.shared.SharedMessageDispatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Validated
public class AdapterMessageDispatchController {

    private final ApplicationInputPortMessageDispatchUseCase messageDispatchUseCase;

    @PostMapping("/dispatch")
    public ResponseEntity<SharedMessageDispatchResponse> dispatchMessage(@Valid @RequestBody @NotNull SharedMessageDispatchRequestParams params) {
        DomainEntityProcessedMessage processedMessage = new DomainEntityProcessedMessage();
        processedMessage.setId(params.getMessageId());
        processedMessage.setPersonalizedContent(params.getContent());
        processedMessage.setPreparedForDispatch(true);
        processedMessage.setDispatchStatus(SharedEnumDeliveryStatus.PENDING);
        processedMessage.setCreatedAt(LocalDateTime.now());
        processedMessage.setUpdatedAt(LocalDateTime.now());

        messageDispatchUseCase.dispatchMessage(processedMessage);

        SharedMessageDispatchResponse response = SharedMessageDispatchResponse.builder()
                .status("Message dispatched successfully")
                .messageId(params.getMessageId())
                .details("Message has been accepted for processing.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delivery-confirmation")
    public ResponseEntity<SharedDeliveryConfirmationResponse> receiveDeliveryConfirmation(@Valid @RequestBody @NotNull SharedDeliveryConfirmationDTO dto) {
        try {
            SharedEnumDeliveryStatus deliveryStatus = SharedEnumDeliveryStatus.valueOf(dto.getDeliveryStatus().toUpperCase());
            messageDispatchUseCase.handleDeliveryAcknowledgment(dto.getMessageId(), deliveryStatus);
        } catch (IllegalArgumentException e) {
            SharedDeliveryConfirmationResponse errorResponse = new SharedDeliveryConfirmationResponse();
            errorResponse.setStatus("Invalid delivery status");
            errorResponse.setMessageId(dto.getMessageId());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        SharedDeliveryConfirmationResponse response = new SharedDeliveryConfirmationResponse();
        response.setStatus("acknowledged");
        response.setMessageId(dto.getMessageId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/error-notification")
    public ResponseEntity<SharedErrorNotificationResponse> receiveErrorNotification(@Valid @RequestBody @NotNull SharedErrorNotificationDTO dto) {
        SharedErrorDetails errorDetails = new SharedErrorDetails(dto.getErrorCode(), dto.getErrorMessage());

        messageDispatchUseCase.handleErrorNotification(dto.getMessageId(), errorDetails);

        SharedErrorNotificationResponse response = new SharedErrorNotificationResponse();
        response.setStatus("acknowledged");
        response.setMessageId(dto.getMessageId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}