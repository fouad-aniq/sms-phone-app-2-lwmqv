package ai.shreds.domain.value_objects;

import lombok.Value;
import lombok.Builder;
import lombok.NonNull;

/**
 * Represents an error notification received from the SMS Gateway Integration Service.
 */
@Value
@Builder
public class DomainValueErrorNotification {

    @NonNull
    private final String messageId;

    @NonNull
    private final String dispatchStatus;

    @NonNull
    private final String errorCode;

    @NonNull
    private final String errorMessage;

}