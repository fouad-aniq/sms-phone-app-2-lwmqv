package ai.shreds.infrastructure.mongodb.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import ai.shreds.infrastructure.entities.DeliveryDetailsEntity;

/**
 * Represents a processed message stored in MongoDB.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "processed_messages")
public class ProcessedMessageMongoEntity {

    @Id
    private String id;

    private String originalMessageId;

    private String personalizedContent;

    private Boolean validationStatus;

    private List<String> businessRulesApplied;

    private Boolean preparedForDispatch;

    private String dispatchStatus;

    private Date dispatchTimestamp;

    private DeliveryDetailsEntity deliveryDetails;
}