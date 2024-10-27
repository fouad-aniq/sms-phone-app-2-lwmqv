package ai.shreds.infrastructure.external_services.grpc;

import io.grpc.stub.StreamObserver;

public class SmsGatewayServiceGrpc {

    public static class SmsGatewayServiceStub {

        public void sendMessage(SendMessageRequest request, StreamObserver<SendMessageResponse> responseObserver) {
            // Implementation for sending a message via gRPC.
            // This is a stub method and should be implemented with actual gRPC call logic.
            // For now, it just simulates a successful response.
            SendMessageResponse response = SendMessageResponse.newBuilder()
                    .setStatus("SUCCESS")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}