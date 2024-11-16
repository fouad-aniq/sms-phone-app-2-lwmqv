package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainEntitySMSMessage;
import ai.shreds.domain.entities.DomainEntityValidationError;
import ai.shreds.domain.ports.DomainPortValidationErrorRepositoryPort;
import ai.shreds.domain.value_objects.DomainValuePhoneNumberValue;
import ai.shreds.domain.value_objects.DomainValueContentValue;
import ai.shreds.domain.value_objects.DomainValueMetadataValue;
import ai.shreds.domain.exceptions.DomainExceptionValidationException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Instant;
import java.time.ZonedDateTime;

public class DomainServiceMessageValidationComponent {

    private final DomainPortValidationErrorRepositoryPort validationErrorRepository;
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final List<String> prohibitedWords = List.of("spam", "scam", "fraud"); // Example prohibited words

    public DomainServiceMessageValidationComponent(DomainPortValidationErrorRepositoryPort validationErrorRepository) {
        this.validationErrorRepository = validationErrorRepository;
    }

    public List<DomainEntityValidationError> validateMessage(DomainEntitySMSMessage message) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (message == null) {
            errors.add(new DomainEntityValidationError(null, null, "ERR000", "Message cannot be null", Instant.now()));
            return errors;
        }
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            errors.add(new DomainEntityValidationError(null, message.getMessageId(), "ERR001", "Message ID is required", Instant.now()));
        }
        errors.addAll(validateRecipientNumber(message.getRecipientNumber()));
        errors.addAll(validateContent(message.getContent()));
        errors.addAll(validateMetadata(message.getMetadata()));
        if (!errors.isEmpty()) {
            errors.forEach(validationErrorRepository::save);
        }
        return errors;
    }

    public List<DomainEntityValidationError> validateRecipientNumber(String phoneNumber) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            errors.add(new DomainEntityValidationError(null, null, "ERR002", "Recipient number is required", Instant.now()));
            return errors;
        }
        try {
            PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, null);
            if (!phoneNumberUtil.isValidNumber(numberProto)) {
                errors.add(new DomainEntityValidationError(null, null, "ERR003", "Invalid recipient number format", Instant.now()));
            }
        } catch (NumberParseException e) {
            errors.add(new DomainEntityValidationError(null, null, "ERR003", "Invalid recipient number format", Instant.now()));
        }
        return errors;
    }

    public List<DomainEntityValidationError> validateContent(String content) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (content == null || content.isEmpty()) {
            errors.add(new DomainEntityValidationError(null, null, "ERR004", "Content is required", Instant.now()));
            return errors;
        }
        if (content.length() > 160) {
            errors.add(new DomainEntityValidationError(null, null, "ERR005", "Content exceeds maximum length of 160 characters", Instant.now()));
        }
        if (containsProhibitedContent(content)) {
            errors.add(new DomainEntityValidationError(null, null, "ERR006", "Content contains prohibited words", Instant.now()));
        }
        return errors;
    }

    private boolean containsProhibitedContent(String content) {
        for (String word : prohibitedWords) {
            if (content.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    public List<DomainEntityValidationError> validateMetadata(Map<String, String> metadata) {
        List<DomainEntityValidationError> errors = new ArrayList<>();
        if (metadata == null || metadata.isEmpty()) {
            return errors;
        }
        if (metadata.containsKey("timestamp")) {
            String timestamp = metadata.get("timestamp");
            try {
                ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeParseException e) {
                errors.add(new DomainEntityValidationError(null, null, "ERR007", "Invalid timestamp format", Instant.now()));
            }
        }
        if (metadata.containsKey("priority")) {
            String priority = metadata.get("priority").toLowerCase();
            if (!priority.equals("high") && !priority.equals("normal") && !priority.equals("low")) {
                errors.add(new DomainEntityValidationError(null, null, "ERR008", "Invalid priority value", Instant.now()));
            }
        }
        if (metadata.containsKey("senderId")) {
            String senderId = metadata.get("senderId");
            if (!isValidSenderId(senderId)) {
                errors.add(new DomainEntityValidationError(null, null, "ERR009", "Invalid sender ID", Instant.now()));
            }
        }
        return errors;
    }

    private boolean isValidSenderId(String senderId) {
        if (senderId == null || senderId.isEmpty()) {
            return false;
        }
        if (senderId.length() < 3 || senderId.length() > 11) {
            return false;
        }
        return senderId.matches("[a-zA-Z0-9]+");
    }
}