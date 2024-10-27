package ai.shreds.infrastructure.external_services.generated;

public class ErrorNotificationResponse {

    private final String status;

    private ErrorNotificationResponse(Builder builder) {
        this.status = builder.status;
    }

    public String getStatus() {
        return status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String status;

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public ErrorNotificationResponse build() {
            return new ErrorNotificationResponse(this);
        }
    }
}