package ai.shreds.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SharedValueScheduleStatus {
    ACTIVE("active"),
    PAUSED("paused"),
    DELETED("deleted");

    private final String value;

    SharedValueScheduleStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SharedValueScheduleStatus fromValue(String value) {
        for (SharedValueScheduleStatus status : SharedValueScheduleStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }
}