package ai.shreds.infrastructure.repositories;

import ai.shreds.domain.entities.DomainEntityProcessedMessage;
import ai.shreds.domain.value_objects.DomainValueDeliveryDetails;
import ai.shreds.shared.SharedEnumDeliveryStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "processed_messages")
public class ProcessedMessageDocument {

    @Id
    private String id;
    private String originalMessageId;
    private String personalizedContent;
    private Boolean validationStatus;
    private List<String> businessRulesApplied;
    private Boolean preparedForDispatch;
    private SharedEnumDeliveryStatus dispatchStatus;
    private LocalDateTime dispatchTimestamp;
    private DomainValueDeliveryDetails deliveryDetails;
    private Integer retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProcessedMessageDocument fromDomainEntity(DomainEntityProcessedMessage domainEntity) {
        ProcessedMessageDocument document = new ProcessedMessageDocument();
        document.id = domainEntity.getId();
        document.originalMessageId = domainEntity.getOriginalMessageId();
        document.personalizedContent = domainEntity.getPersonalizedContent();
        document.validationStatus = domainEntity.getValidationStatus();
        document.businessRulesApplied = domainEntity.getBusinessRulesApplied();
        document.preparedForDispatch = domainEntity.getPreparedForDispatch();
        document.dispatchStatus = domainEntity.getDispatchStatus();
        document.dispatchTimestamp = domainEntity.getDispatchTimestamp();
        document.deliveryDetails = domainEntity.getDeliveryDetails();
        document.retryCount = domainEntity.getRetryCount();
        document.createdAt = domainEntity.getCreatedAt();
        document.updatedAt = domainEntity.getUpdatedAt();
        return document;
    }

    public DomainEntityProcessedMessage toDomainEntity() {
        return DomainEntityProcessedMessage.builder()
                .id(this.id)
                .originalMessageId(this.originalMessageId)
                .personalizedContent(this.personalizedContent)
                .validationStatus(this.validationStatus)
                .businessRulesApplied(this.businessRulesApplied)
                .preparedForDispatch(this.preparedForDispatch)
                .dispatchStatus(this.dispatchStatus)
                .dispatchTimestamp(this.dispatchTimestamp)
                .deliveryDetails(this.deliveryDetails)
                .retryCount(this.retryCount)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalMessageId() {
        return originalMessageId;
    }

    public void setOriginalMessageId(String originalMessageId) {
        this.originalMessageId = originalMessageId;
    }

    public String getPersonalizedContent() {
        return personalizedContent;
    }

    public void setPersonalizedContent(String personalizedContent) {
        this.personalizedContent = personalizedContent;
    }

    public Boolean getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(Boolean validationStatus) {
        this.validationStatus = validationStatus;
    }

    public List<String> getBusinessRulesApplied() {
        return businessRulesApplied;
    }

    public void setBusinessRulesApplied(List<String> businessRulesApplied) {
        this.businessRulesApplied = businessRulesApplied;
    }

    public Boolean getPreparedForDispatch() {
        return preparedForDispatch;
    }

    public void setPreparedForDispatch(Boolean preparedForDispatch) {
        this.preparedForDispatch = preparedForDispatch;
    }

    public SharedEnumDeliveryStatus getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(SharedEnumDeliveryStatus dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public LocalDateTime getDispatchTimestamp() {
        return dispatchTimestamp;
    }

    public void setDispatchTimestamp(LocalDateTime dispatchTimestamp) {
        this.dispatchTimestamp = dispatchTimestamp;
    }

    public DomainValueDeliveryDetails getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DomainValueDeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}