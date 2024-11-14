package ai.shreds.shared;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SharedResponseDTO {
    private final String status;
    private final String message;
    private final String messageId;
    private final List<String> errors;

    // Factory method for success response
    public static SharedResponseDTO success(String messageId, String message) {
        return SharedResponseDTO.builder()
                .status("SUCCESS")
                .message(message)
                .messageId(messageId)
                .build();
    }

    // Factory method for error response
    public static SharedResponseDTO error(String messageId, String message, List<String> errors) {
        return SharedResponseDTO.builder()
                .status("ERROR")
                .message(message)
                .messageId(messageId)
                .errors(errors)
                .build();
    }
}