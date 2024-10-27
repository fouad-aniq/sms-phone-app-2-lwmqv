package ai.shreds.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Represents a validation policy used within the message processing system.
 */
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class SharedPolicy {
    /**
     * Unique identifier for the policy.
     */
    @NonNull
    @NotBlank
    private final String id;

    /**
     * Name of the policy.
     */
    @NonNull
    @NotBlank
    private final String name;

    /**
     * Description of the policy.
     */
    private final String description;

    /**
     * The rule or condition that defines the policy.
     */
    @NonNull
    @NotBlank
    private final String criteria;

    /**
     * Indicates if the policy is currently active.
     */
    private final boolean isActive;
}