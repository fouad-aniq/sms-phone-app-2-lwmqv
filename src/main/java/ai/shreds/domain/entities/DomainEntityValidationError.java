package ai.shreds.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "validation_errors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntityValidationError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer errorId;

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "messageId")
    private DomainEntitySMSMessage message;

    @Column(nullable = false)
    private String errorCode;

    @Column(nullable = false)
    private String errorMessage;

    @Column(nullable = false)
    private Timestamp timestamp;

    public DomainEntityValidationError(String messageId, String errorCode, String errorMessage, Timestamp timestamp) {
        this.message = new DomainEntitySMSMessage(); // Assuming a constructor or method to set messageId
        this.message.setMessageId(messageId);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    // Additional methods if needed
}
