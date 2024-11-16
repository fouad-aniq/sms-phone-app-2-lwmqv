package ai.shreds.shared;

/**
 * Enum representing the possible statuses of an SMS message.
 */
public enum SharedEnumMessageStatusEnum {

    /**
     * The message has been received but not yet processed.
     */
    RECEIVED,

    /**
     * The message has passed validation.
     */
    VALIDATED,

    /**
     * The message has failed validation.
     */
    FAILED
}
