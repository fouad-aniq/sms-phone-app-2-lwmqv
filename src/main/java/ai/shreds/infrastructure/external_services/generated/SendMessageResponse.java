package ai.shreds.infrastructure.external_services.generated;

public class SendMessageResponse {

    private final String status;
    private final String messageId;
    private final String errorMessage;

    public SendMessageResponse(String status, String messageId, String errorMessage) {
        this.status = status;
        this.messageId = messageId;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class Builder {
        private String status;
        private String messageId;
        private String errorMessage;

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public SendMessageResponse build() {
            return new SendMessageResponse(status, messageId, errorMessage);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}