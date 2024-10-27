package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.entities.DomainEntityDeliveryDetails;
import ai.shreds.domain.ports.DomainPortSmsGatewayClient;
import ai.shreds.domain.ports.DomainPortMessageRepository;
import ai.shreds.shared.SharedDispatchResponseDTO;
import ai.shreds.shared.SharedEnumDispatchStatus;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.decorators.CheckedRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.Date;

public class DomainServiceMessageDispatch {

    private static final Logger logger = LoggerFactory.getLogger(DomainServiceMessageDispatch.class);

    private final DomainPortSmsGatewayClient smsGatewayClient;
    private final DomainPortMessageRepository messageRepository;

    private final CircuitBreaker circuitBreaker;

    public DomainServiceMessageDispatch(DomainPortSmsGatewayClient smsGatewayClient,
                                        DomainPortMessageRepository messageRepository) {
        this.smsGatewayClient = smsGatewayClient;
        this.messageRepository = messageRepository;

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .build();
        this.circuitBreaker = CircuitBreaker.of('smsGatewayCircuitBreaker', circuitBreakerConfig);
    }

    public void dispatchMessage(DomainEntityProcessedMessage message) {
        if (!message.getValidationStatus() || !message.getPreparedForDispatch()) {
            logger.error('Message {} is not valid for dispatch.', message.getId());
            throw new IllegalStateException('Message is not valid for dispatch.');
        }
        try {
            CheckedRunnable decoratedSendMessage = Decorators.ofCheckedRunnable(() -> {
                smsGatewayClient.sendMessage(message);
            }).withCircuitBreaker(circuitBreaker).decorate();

            decoratedSendMessage.run();

            message.setDispatchStatus(SharedEnumDispatchStatus.SENT);
            message.setDispatchTimestamp(new Date());
            messageRepository.save(message);

            logger.info('Message {} dispatched successfully.', message.getId());
        } catch (Exception e) {
            logger.error('Error dispatching message {}: {}', message.getId(), e.getMessage());
            executeRetryMechanism(message);
        }
    }

    public void handleDispatchResponse(SharedDispatchResponseDTO response) {
        DomainEntityProcessedMessage message = messageRepository.findById(response.getMessageId());
        if (message != null) {
            message.setDispatchStatus(SharedEnumDispatchStatus.valueOf(response.getDispatchStatus().toUpperCase()));
            DomainEntityDeliveryDetails deliveryDetails = new DomainEntityDeliveryDetails();
            deliveryDetails.setMessageId(response.getMessageId());
            deliveryDetails.setDeliveryStatus(response.getDispatchStatus());
            deliveryDetails.setDeliveryTimestamp(response.getDeliveryTimestamp());
            deliveryDetails.setProviderResponse(response.getDetails());
            message.setDeliveryDetails(deliveryDetails);
            messageRepository.save(message);

            logger.info('Dispatch response handled for message {}.', response.getMessageId());
        } else {
            logger.error('Message not found for ID: {}', response.getMessageId());
            throw new IllegalArgumentException('Message not found for ID: ' + response.getMessageId());
        }
    }

    public void executeRetryMechanism(DomainEntityProcessedMessage message) {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        Retry retry = Retry.of('smsSendRetry', retryConfig);

        CheckedRunnable retryableRunnable = Decorators.ofCheckedRunnable(() -> {
            smsGatewayClient.sendMessage(message);
        }).withRetry(retry).withCircuitBreaker(circuitBreaker).decorate();

        try {
            retryableRunnable.run();
            message.setDispatchStatus(SharedEnumDispatchStatus.SENT);
            message.setDispatchTimestamp(new Date());
            messageRepository.save(message);

            logger.info('Message {} dispatched successfully after retry.', message.getId());
        } catch (Throwable t) {
            message.setDispatchStatus(SharedEnumDispatchStatus.FAILED);
            messageRepository.save(message);

            logger.error('Failed to dispatch message {} after retries: {}', message.getId(), t.getMessage());
        }
    }

    public void updateMessageStatus(String messageId, SharedEnumDispatchStatus status) {
        DomainEntityProcessedMessage message = messageRepository.findById(messageId);
        if (message != null) {
            message.setDispatchStatus(status);
            messageRepository.save(message);

            logger.info('Message {} status updated to {}.', messageId, status);
        } else {
            logger.error('Message not found for ID: {}', messageId);
        }
    }
}