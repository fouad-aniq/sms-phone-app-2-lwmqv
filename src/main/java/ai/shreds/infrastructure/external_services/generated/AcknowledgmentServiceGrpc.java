package ai.shreds.infrastructure.external_services.generated;

import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.AbstractStub;

public class AcknowledgmentServiceGrpc {

    public abstract static class AcknowledgmentServiceImplBase extends AbstractStub<AcknowledgmentServiceImplBase> implements BindableService {

        protected AcknowledgmentServiceImplBase(io.grpc.Channel channel) {
            super(channel);
        }

        protected AcknowledgmentServiceImplBase(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected AcknowledgmentServiceImplBase build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new AcknowledgmentServiceImplBase(channel, callOptions) {
                @Override
                public void sendDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request, StreamObserver<DeliveryAcknowledgmentResponse> responseObserver) {
                    // Implementation goes here
                }

                @Override
                public void sendErrorNotification(ErrorNotificationRequest request, StreamObserver<ErrorNotificationResponse> responseObserver) {
                    // Implementation goes here
                }
            };
        }

        public abstract void sendDeliveryAcknowledgment(DeliveryAcknowledgmentRequest request, StreamObserver<DeliveryAcknowledgmentResponse> responseObserver);

        public abstract void sendErrorNotification(ErrorNotificationRequest request, StreamObserver<ErrorNotificationResponse> responseObserver);

        @Override
        public final ServerServiceDefinition bindService() {
            // Implement the method to bind service
            return null; // Replace with actual implementation
        }
    }
}