package ai.shreds.infrastructure.external_services;

import ai.shreds.domain.entities.DomainEntityMessageProcessingEvent;
import ai.shreds.domain.ports.DomainPortMessageProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InfrastructureKafkaMessageProducerImpl implements DomainPortMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.message-processing:message-processing}")
    private String topic;

    public InfrastructureKafkaMessageProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    @Retryable(
        value = {Exception.class},
        maxAttemptsExpression = "${retry.maxAttempts}",
        backoff = @Backoff(
            delayExpression = "${retry.backoff.delay}",
            multiplierExpression = "${retry.backoff.multiplier}"
        )
    )
    public void sendMessage(DomainEntityMessageProcessingEvent event) {
        String key = event.getScheduleId().toString();
        try {
            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(topic, key, message);
            log.info("Message sent to Kafka topic {} with key {}", topic, key);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event for Kafka message production", e);
            throw new IllegalArgumentException("Failed to serialize event", e);
        } catch (Exception e) {
            log.error("Error sending message to Kafka topic {} with key {}", topic, key, e);
            throw e;
        }
    }

    @Recover
    public void recover(Exception e, DomainEntityMessageProcessingEvent event) {
        log.error("Retries exhausted for message with key {}. Failed to send message to Kafka.", event.getScheduleId(), e);
        // Implement additional error handling logic here, such as sending alerts or notifications
    }
}