package ai.shreds.infrastructure.external_services.grpc;

public class SendMessageRequest {
    private final String messageId;
    private final String originalMessageId;
    private final String personalizedContent;

    private SendMessageRequest(Builder builder) {
        this.messageId = builder.messageId;
        this.originalMessageId = builder.originalMessageId;
        this.personalizedContent = builder.personalizedContent;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String messageId;
        private String originalMessageId;
        private String personalizedContent;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setOriginalMessageId(String originalMessageId) {
            this.originalMessageId = originalMessageId;
            return this;
        }

        public Builder setPersonalizedContent(String personalizedContent) {
            this.personalizedContent = personalizedContent;
            return this;
        }

        public SendMessageRequest build() {
            return new SendMessageRequest(this);
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public String getOriginalMessageId() {
        return originalMessageId;
    }

    public String getPersonalizedContent() {
        return personalizedContent;
    }
}