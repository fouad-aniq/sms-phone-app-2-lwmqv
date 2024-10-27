package ai.shreds.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntityBusinessRule implements Serializable {
    private String id;
    private String name;
    private String description;
    private String logic;
    private Boolean isActive;
}