package ai.shreds.infrastructure.external_services.generated;

import com.google.protobuf.Timestamp;

public class DeliveryAcknowledgmentRequest {

    private final String messageId;
    private final String dispatchStatus;
    private final Timestamp deliveryTimestamp;
    private final String details;

    private DeliveryAcknowledgmentRequest(Builder builder) {
        this.messageId = builder.messageId;
        this.dispatchStatus = builder.dispatchStatus;
        this.deliveryTimestamp = builder.deliveryTimestamp;
        this.details = builder.details;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDispatchStatus() {
        return dispatchStatus;
    }

    public Timestamp getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public String getDetails() {
        return details;
    }

    public static class Builder {
        private String messageId;
        private String dispatchStatus;
        private Timestamp deliveryTimestamp;
        private String details;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setDispatchStatus(String dispatchStatus) {
            this.dispatchStatus = dispatchStatus;
            return this;
        }

        public Builder setDeliveryTimestamp(Timestamp deliveryTimestamp) {
            this.deliveryTimestamp = deliveryTimestamp;
            return this;
        }

        public Builder setDetails(String details) {
            this.details = details;
            return this;
        }

        public DeliveryAcknowledgmentRequest build() {
            return new DeliveryAcknowledgmentRequest(this);
        }
    }
}