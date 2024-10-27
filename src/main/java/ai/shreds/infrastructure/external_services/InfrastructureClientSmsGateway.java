package ai.shreds.infrastructure.external_services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.ports.DomainPortSmsGatewayClient;
import ai.shreds.external.smsgateway.SendMessageRequest;
import ai.shreds.external.smsgateway.SendMessageResponse;
import ai.shreds.external.smsgateway.SmsGatewayServiceGrpc;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * InfrastructureClientSmsGateway implements the DomainPortSmsGatewayClient interface.
 * It sends messages to the SMS Gateway Integration Service via gRPC.
 */
@Slf4j
@Component
public class InfrastructureClientSmsGateway implements DomainPortSmsGatewayClient {

    private final SmsGatewayServiceGrpc.SmsGatewayServiceBlockingStub smsGatewayStub;

    @Autowired
    public InfrastructureClientSmsGateway(SmsGatewayServiceGrpc.SmsGatewayServiceBlockingStub smsGatewayStub) {
        this.smsGatewayStub = smsGatewayStub;
    }

    /**
     * Sends a processed message to the SMS Gateway Integration Service via gRPC.
     *
     * @param message the processed message to be sent
     */
    @Override
    @Retry(name = "smsGateway", fallbackMethod = "sendMessageFallback")
    public void sendMessage(DomainEntityProcessedMessage message) {
        SendMessageRequest request = SendMessageRequest.newBuilder()
                .setMessageId(message.getId())
                .setRecipientNumber(message.getRecipientNumber())
                .setContent(message.getPersonalizedContent())
                .build();

        try {
            SendMessageResponse response = smsGatewayStub.sendMessage(request);
            log.info("Message sent successfully. Message ID: {}", message.getId());
        } catch (Exception e) {
            log.error("Failed to send message with ID: {}", message.getId(), e);
            throw e;
        }
    }

    /**
     * Fallback method invoked when retries are exhausted.
     *
     * @param message   the processed message that failed to send
     * @param throwable the exception that was thrown
     */
    public void sendMessageFallback(DomainEntityProcessedMessage message, Throwable throwable) {
        log.error("Fallback executed for message ID {} after retries. Exception: {}", message.getId(), throwable.getMessage());
    }
}