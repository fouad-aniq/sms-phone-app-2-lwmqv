package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.exceptions.DomainExceptionMessageDispatch;
import ai.shreds.domain.ports.DomainPortMessageRepository;
import ai.shreds.domain.ports.DomainPortSmsGatewayClient;
import ai.shreds.domain.value_objects.DomainValueDispatchStatus;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DomainServiceMessageDispatch {

    private final DomainPortMessageRepository messageRepositoryPort;
    private final DomainPortSmsGatewayClient smsGatewayClientPort;

    public void sendMessage(DomainEntityProcessedMessage message) {
        if (message.isPreparedForDispatch() && message.getValidationStatus()) {
            try {
                smsGatewayClientPort.sendMessage(message);
                updateMessageStatus(message.getId(), DomainValueDispatchStatus.SENT);
            } catch (Exception e) {
                executeRetryMechanism(message);
            }
        } else {
            throw new DomainExceptionMessageDispatch("Message is not ready for dispatch.");
        }
    }

    public void executeRetryMechanism(DomainEntityProcessedMessage message) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(5))
                .build();
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("messageDispatchRetry", config);

        Supplier<Void> retryableSupplier = Retry.decorateSupplier(retry, () -> {
            smsGatewayClientPort.sendMessage(message);
            return null;
        });

        try {
            retryableSupplier.get();
            updateMessageStatus(message.getId(), DomainValueDispatchStatus.SENT);
        } catch (Exception e) {
            updateMessageStatus(message.getId(), DomainValueDispatchStatus.FAILED);
            throw new DomainExceptionMessageDispatch("Failed to dispatch message after retries.");
        }
    }

    public void updateMessageStatus(String messageId, DomainValueDispatchStatus status) {
        DomainEntityProcessedMessage message = messageRepositoryPort.findById(messageId);
        if (message != null) {
            message.setDispatchStatus(status);
            messageRepositoryPort.save(message);
        } else {
            throw new DomainExceptionMessageDispatch("Message not found for ID: " + messageId);
        }
    }
}