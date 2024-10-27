package ai.shreds.adapter.primary;

import ai.shreds.application.ports.ApplicationHandleAcknowledgmentInputPort;
import ai.shreds.shared.SharedAcknowledgmentDTO;
import ai.shreds.shared.SharedErrorNotificationDTO;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
public class AdapterAcknowledgmentGrpcService extends AcknowledgmentServiceGrpc.AcknowledgmentServiceImplBase {

    @Autowired
    private ApplicationHandleAcknowledgmentInputPort acknowledgmentInputPort;

    @Override
    public void handleDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request, StreamObserver<DeliveryAcknowledgmentResponse> responseObserver) {
        SharedAcknowledgmentDTO acknowledgment = SharedAcknowledgmentDTO.builder()
                .messageId(request.getMessageId())
                .dispatchStatus(request.getDispatchStatus())
                .deliveryTimestamp(new Date(request.getDeliveryTimestamp()))
                .details(request.getDetails())
                .build();

        acknowledgmentInputPort.processAcknowledgment(acknowledgment);

        DeliveryAcknowledgmentResponse response = DeliveryAcknowledgmentResponse.newBuilder()
                .setStatus("Acknowledgment received")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void handleErrorNotification(ErrorNotificationRequest request, StreamObserver<ErrorNotificationResponse> responseObserver) {
        SharedErrorNotificationDTO notification = SharedErrorNotificationDTO.builder()
                .messageId(request.getMessageId())
                .dispatchStatus(request.getDispatchStatus())
                .errorCode(request.getErrorCode())
                .errorMessage(request.getErrorMessage())
                .build();

        acknowledgmentInputPort.processErrorNotification(notification);

        ErrorNotificationResponse response = ErrorNotificationResponse.newBuilder()
                .setStatus("Error notification received")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}