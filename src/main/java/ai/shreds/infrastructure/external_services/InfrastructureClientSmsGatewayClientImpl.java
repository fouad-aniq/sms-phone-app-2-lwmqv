package ai.shreds.infrastructure.external_services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortSmsGatewayClient;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureClientSmsGatewayClientImpl implements DomainPortSmsGatewayClient {

    private static final Logger logger = LoggerFactory.getLogger(InfrastructureClientSmsGatewayClientImpl.class);

    @GrpcClient("smsGatewayClient")
    private SmsGatewayServiceGrpc.SmsGatewayServiceStub smsGatewayServiceStub;

    @Override
    @Retry(name = "smsGatewayRetry")
    @CircuitBreaker(name = "smsGatewayCircuitBreaker")
    public void sendMessage(DomainEntityProcessedMessage message) {
        SendMessageRequest request = mapToSendMessageRequest(message);
        smsGatewayServiceStub.sendMessage(request, new io.grpc.stub.StreamObserver<SendMessageResponse>() {
            @Override
            public void onNext(SendMessageResponse response) {
                logger.info("Message sent successfully: {}", response.getStatus());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Failed to send message: {}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Do nothing
            }
        });
    }

    private SendMessageRequest mapToSendMessageRequest(DomainEntityProcessedMessage message) {
        return SendMessageRequest.newBuilder()
                .setMessageId(message.getId())
                .setOriginalMessageId(message.getOriginalMessageId())
                .setPersonalizedContent(message.getPersonalizedContent())
                .build();
    }
}