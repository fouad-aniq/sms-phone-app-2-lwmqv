package ai.shreds.infrastructure.external_services.generated;

public class ErrorNotificationRequest {

    private final String messageId;
    private final String dispatchStatus;
    private final String errorCode;
    private final String errorMessage;

    private ErrorNotificationRequest(Builder builder) {
        this.messageId = builder.messageId;
        this.dispatchStatus = builder.dispatchStatus;
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDispatchStatus() {
        return dispatchStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class Builder {
        private String messageId;
        private String dispatchStatus;
        private String errorCode;
        private String errorMessage;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setDispatchStatus(String dispatchStatus) {
            this.dispatchStatus = dispatchStatus;
            return this;
        }

        public Builder setErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ErrorNotificationRequest build() {
            return new ErrorNotificationRequest(this);
        }
    }
}