package ai.shreds.infrastructure.external_services.generated;

public class SendMessageRequest {

    private final String messageId;
    private final String recipientNumber;
    private final String content;

    private SendMessageRequest(Builder builder) {
        this.messageId = builder.messageId;
        this.recipientNumber = builder.recipientNumber;
        this.content = builder.content;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getContent() {
        return content;
    }

    public static class Builder {
        private String messageId;
        private String recipientNumber;
        private String content;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setRecipientNumber(String recipientNumber) {
            this.recipientNumber = recipientNumber;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public SendMessageRequest build() {
            return new SendMessageRequest(this);
        }
    }
}