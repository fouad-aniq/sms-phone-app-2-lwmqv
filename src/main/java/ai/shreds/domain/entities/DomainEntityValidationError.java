package ai.shreds.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "validation_errors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainEntityValidationError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_id")
    private Integer errorId;

    @Column(name = "message_id", nullable = false, length = 50)
    private String messageId;

    @Column(name = "error_code", nullable = false)
    private String errorCode;

    @Column(name = "error_message", nullable = false)
    private String errorMessage;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", insertable = false, updatable = false)
    private DomainEntitySMSMessage smsMessage;

    // Constructors, getters, and setters are generated by Lombok's annotations

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainEntityValidationError that = (DomainEntityValidationError) o;

        return errorId != null ? errorId.equals(that.errorId) : that.errorId == null;
    }

    @Override
    public int hashCode() {
        return errorId != null ? errorId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DomainEntityValidationError{" +
                "errorId=" + errorId +
                ", messageId='" + messageId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}