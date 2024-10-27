package ai.shreds.proto;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;

public class AcknowledgmentServiceGrpc {

    public static abstract class AcknowledgmentServiceImplBase implements BindableService {

        public void handleDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request,
                                                 StreamObserver<DeliveryAcknowledgmentResponse> responseObserver) {
            // Method to be overridden in the implementing class
        }

        public void handleErrorNotification(ErrorNotificationRequest request,
                                            StreamObserver<ErrorNotificationResponse> responseObserver) {
            // Method to be overridden in the implementing class
        }
    }

    // Additional utility methods and classes can be added here if needed
}