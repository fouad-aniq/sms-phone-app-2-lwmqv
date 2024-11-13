package ai.shreds.shared;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedValidationResponse {
    private Boolean valid;
    private List<String> errors;
}