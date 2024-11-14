package ai.shreds.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SharedSMSMessageDTO {

    @NotNull(message = "Message ID is required")
    @JsonProperty("messageId")
    private String messageId;

    @NotNull(message = "Recipient number is required")
    @Pattern(
        regexp = "\\+[1-9]\\d{1,14}$",
        message = "Recipient number must be a valid international number starting with +"
    )
    @JsonProperty("recipientNumber")
    private String recipientNumber;

    @NotNull(message = "Content is required")
    @Size(
        max = 160,
        message = "Content must not exceed 160 characters"
    )
    @JsonProperty("content")
    private String content;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    // Additional validation logic can be added here if needed
}