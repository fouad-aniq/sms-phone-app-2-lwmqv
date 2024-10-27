package ai.shreds.infrastructure.external_services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortSmsGatewayClient;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.devh.boot.grpc.client.inject.GrpcClient;
import ai.shreds.infrastructure.external_services.grpc.SmsGatewayServiceGrpc;
import ai.shreds.infrastructure.external_services.grpc.SendMessageRequest;
import ai.shreds.infrastructure.external_services.grpc.SendMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
        Mono<SendMessageResponse> responseMono = Mono.create(sink ->
            smsGatewayServiceStub.sendMessage(request, new io.grpc.stub.StreamObserver<SendMessageResponse>() {
                @Override
                public void onNext(SendMessageResponse response) {
                    sink.success(response);
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    // Do nothing
                }
            })
        );

        responseMono.subscribe(
            response -> logger.info("Message sent successfully: {}", response.getStatus()),
            error -> logger.error("Failed to send message: {}", error.getMessage())
        );
    }

    private SendMessageRequest mapToSendMessageRequest(DomainEntityProcessedMessage message) {
        return SendMessageRequest.newBuilder()
                .setMessageId(message.getId())
                .setOriginalMessageId(message.getOriginalMessageId())
                .setPersonalizedContent(message.getPersonalizedContent())
                .build();
    }
}