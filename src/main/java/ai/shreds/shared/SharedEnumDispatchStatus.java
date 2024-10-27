package ai.shreds.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SharedEnumDispatchStatus {
    PENDING("pending"),
    SENT("sent"),
    DELIVERED("delivered"),
    FAILED("failed"),
    RETRYING("retrying");

    private final String status;

    SharedEnumDispatchStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static SharedEnumDispatchStatus fromValue(String value) {
        for (SharedEnumDispatchStatus status : SharedEnumDispatchStatus.values()) {
            if (status.status.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown dispatch status: " + value);
    }
}