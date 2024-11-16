package ai.shreds.shared;

/**
 * Enum representing the possible statuses of an SMS message.
 * This enum is used throughout the application to represent the current state of a message as it moves through the processing pipeline.
 */
public enum SharedEnumMessageStatusEnum {
    RECEIVED,
    VALIDATED,
    FAILED;
}