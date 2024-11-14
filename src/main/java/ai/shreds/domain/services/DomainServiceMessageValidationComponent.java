package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.exceptions.DomainExceptionValidationException;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.shared.SharedEnumMessageStatusEnum;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DomainServiceMessageValidationComponent {

    private final DomainPortValidationErrorRepositoryPort validationErrorRepository;
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final Set<String> prohibitedContent;
    private String currentMessageId;

    public DomainServiceMessageValidationComponent(DomainPortValidationErrorRepositoryPort validationErrorRepository, Set<String> prohibitedContent) {
        this.validationErrorRepository = validationErrorRepository;
        this.prohibitedContent = prohibitedContent;
    }

    public List<DomainEntityValidationError> validateMessage(DomainEntitySMSMessage message) {
        List<DomainEntityValidationError> validationErrors = new ArrayList<>();
        currentMessageId = message.getMessageId();

        // Check required fields
        if (currentMessageId == null || currentMessageId.isEmpty()) {
            validationErrors.add(createValidationError("ERR001", "Message ID is required."));
        }
        if (message.getRecipientNumber() == null || message.getRecipientNumber().isEmpty()) {
            validationErrors.add(createValidationError("ERR002", "Recipient number is required."));
        }
        if (message.getContent() == null || message.getContent().isEmpty()) {
            validationErrors.add(createValidationError("ERR003", "Message content is required."));
        }

        // Validate recipient number
        validationErrors.addAll(validateRecipientNumber(message.getRecipientNumber()));

        // Validate content
        validationErrors.addAll(validateContent(message.getContent()));

        // Validate metadata
        validationErrors.addAll(validateMetadata(message.getMetadata()));

        // Set message status
        if (validationErrors.isEmpty()) {
            message.setStatus(SharedEnumMessageStatusEnum.VALIDATED);
        } else {
            message.setStatus(SharedEnumMessageStatusEnum.FAILED);
            // Save validation errors
            for (DomainEntityValidationError error : validationErrors) {
                validationErrorRepository.save(error);
            }
        }

        return validationErrors;
    }

    public List<DomainEntityValidationError> validateRecipientNumber(String phoneNumber) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            errors.add(createValidationError("ERR002", "Recipient number is required."));
            return errors;
        }
        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, null);
            if (!phoneNumberUtil.isValidNumber(numberProto) || !phoneNumber.startsWith("+")) {
                errors.add(createValidationError("ERR004", "Invalid phone number format."));
            }
        } catch (NumberParseException e) {
            errors.add(createValidationError("ERR004", "Invalid phone number format."));
        }
        return errors;
    }

    public List<DomainEntityValidationError> validateContent(String content) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (content == null || content.isEmpty()) {
            errors.add(createValidationError("ERR003", "Message content is required."));
            return errors;
        }
        if (content.length() > 160) {
            errors.add(createValidationError("ERR005", "Message content exceeds 160 characters."));
        }
        for (String prohibitedWord : prohibitedContent) {
            if (content.toLowerCase().contains(prohibitedWord.toLowerCase())) {
                errors.add(createValidationError("ERR006", "Message content contains prohibited content."));
                break;
            }
        }
        return errors;
    }

    public List<DomainEntityValidationError> validateMetadata(Map<String, String> metadata) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (metadata != null) {
            if (metadata.containsKey("timestamp")) {
                String timestamp = metadata.get("timestamp");
                try {
                    OffsetDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                } catch (DateTimeParseException e) {
                    errors.add(createValidationError("ERR007", "Invalid timestamp format."));
                }
            }
            // Validate other metadata fields as required
        }
        return errors;
    }

    private DomainEntityValidationError createValidationError(String errorCode, String errorMessage) {
        DomainEntityValidationError error = new DomainEntityValidationError();
        error.setMessageId(currentMessageId);
        error.setErrorCode(errorCode);
        error.setErrorMessage(errorMessage);
        error.setTimestamp(OffsetDateTime.now());
        return error;
    }
}
