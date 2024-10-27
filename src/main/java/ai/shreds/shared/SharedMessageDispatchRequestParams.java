package ai.shreds.shared;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class SharedMessageDispatchRequestParams {

    @NotNull(message = "messageId cannot be null")
    private String messageId;

    @NotNull(message = "recipient cannot be null")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid recipient phone number format")
    private String recipient;

    @NotNull(message = "content cannot be null")
    private String content;
}