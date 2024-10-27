package ai.shreds.infrastructure.external_services.grpc;

public class SendMessageResponse {
    private final String status;

    private SendMessageResponse(Builder builder) {
        this.status = builder.status;
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

        public SendMessageResponse build() {
            return new SendMessageResponse(this);
        }
    }

    public String getStatus() {
        return status;
    }
}