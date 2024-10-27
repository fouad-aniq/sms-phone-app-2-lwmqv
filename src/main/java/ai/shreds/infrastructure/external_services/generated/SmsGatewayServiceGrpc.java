package ai.shreds.infrastructure.external_services.generated;

import io.grpc.ManagedChannel;
import io.grpc.stub.AbstractStub;

public class SmsGatewayServiceGrpc {

    /**
     * Creates a new blocking stub that supports all call types for the service.
     *
     * @param channel the channel to use for communication
     * @return a new blocking stub instance
     */
    public static SmsGatewayServiceBlockingStub newBlockingStub(ManagedChannel channel) {
        return new SmsGatewayServiceBlockingStub(channel);
    }

    /**
     * Stub for making blocking calls to the SMS Gateway service.
     */
    public static class SmsGatewayServiceBlockingStub extends AbstractStub<SmsGatewayServiceBlockingStub> {

        protected SmsGatewayServiceBlockingStub(ManagedChannel channel) {
            super(channel);
        }

        protected SmsGatewayServiceBlockingStub(ManagedChannel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        // Removed the '@Override' annotation as it does not override any method in the superclass
        protected SmsGatewayServiceBlockingStub build(ManagedChannel channel, io.grpc.CallOptions callOptions) {
            return new SmsGatewayServiceBlockingStub(channel, callOptions);
        }

        // Example method to simulate sending a message
        public SendMessageResponse sendMessage(SendMessageRequest request) {
            // Simulate sending a message and receiving a response.
            // In a real implementation, this would involve gRPC communication logic.
            return SendMessageResponse.newBuilder().setStatus("SUCCESS").build();
        }
    }
}