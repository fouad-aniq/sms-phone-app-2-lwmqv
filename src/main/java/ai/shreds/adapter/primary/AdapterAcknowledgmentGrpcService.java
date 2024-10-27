package ai.shreds.adapter.primary;

import ai.shreds.shared.SharedDeliveryAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;
import ai.shreds.application.ports.ApplicationInputPortHandleAcknowledgment;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import ai.shreds.proto.AcknowledgmentServiceGrpc;
import ai.shreds.proto.DeliveryAcknowledgmentRequest;
import ai.shreds.proto.DeliveryAcknowledgmentResponse;
import ai.shreds.proto.ErrorNotificationRequest;
import ai.shreds.proto.ErrorNotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class AdapterAcknowledgmentGrpcService extends AcknowledgmentServiceGrpc.AcknowledgmentServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(AdapterAcknowledgmentGrpcService.class);

    private final ApplicationInputPortHandleAcknowledgment acknowledgmentService;

    @Override
    public void sendDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request, StreamObserver<DeliveryAcknowledgmentResponse> responseObserver) {
        try {
            SharedDeliveryAcknowledgmentDTO acknowledgmentDTO = mapToDeliveryAcknowledgmentDTO(request);
            acknowledgmentService.processAcknowledgment(acknowledgmentDTO);
            DeliveryAcknowledgmentResponse response = DeliveryAcknowledgmentResponse.newBuilder()
                    .setStatus("SUCCESS")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error processing delivery acknowledgment", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void sendErrorNotification(ErrorNotificationRequest request, StreamObserver<ErrorNotificationResponse> responseObserver) {
        try {
            SharedErrorNotificationDTO errorNotificationDTO = mapToErrorNotificationDTO(request);
            acknowledgmentService.processErrorNotification(errorNotificationDTO);
            ErrorNotificationResponse response = ErrorNotificationResponse.newBuilder()
                    .setStatus("SUCCESS")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error processing error notification", e);
            responseObserver.onError(e);
        }
    }

    private SharedDeliveryAcknowledgmentDTO mapToDeliveryAcknowledgmentDTO(DeliveryAcknowledgmentRequest request) {
        Instant deliveryTimestamp = Instant.ofEpochSecond(request.getDeliveryTimestamp().getSeconds(), request.getDeliveryTimestamp().getNanos());
        return SharedDeliveryAcknowledgmentDTO.builder()
                .messageId(request.getMessageId())
                .dispatchStatus(request.getDispatchStatus())
                .deliveryTimestamp(deliveryTimestamp)
                .details(request.getDetails())
                .build();
    }

    private SharedErrorNotificationDTO mapToErrorNotificationDTO(ErrorNotificationRequest request) {
        return SharedErrorNotificationDTO.builder()
                .messageId(request.getMessageId())
                .dispatchStatus(request.getDispatchStatus())
                .errorCode(request.getErrorCode())
                .errorMessage(request.getErrorMessage())
                .build();
    }
}