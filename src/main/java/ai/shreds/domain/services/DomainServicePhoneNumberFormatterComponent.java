package ai.shreds.domain.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class DomainServicePhoneNumberFormatterComponent {

    private static final Logger logger = LoggerFactory.getLogger(DomainServicePhoneNumberFormatterComponent.class);
    private final PhoneNumberUtil phoneNumberUtil;

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            logger.error("Phone number is null or empty");
            return false;
        }
        try {
            PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, "US"); // Default to US if no region
            return phoneNumberUtil.isValidNumber(parsedNumber);
        } catch (NumberParseException e) {
            logger.error("Number parsing failed", e);
            return false;
        }
    }

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            logger.error("Phone number is null or empty");
            return null;
        }
        try {
            PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, "US"); // Default to US if no region
            if (phoneNumberUtil.isValidNumber(parsedNumber)) {
                return phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            } else {
                logger.warn("Invalid phone number: {}", phoneNumber);
                return null;
            }
        } catch (NumberParseException e) {
            logger.error("Failed to format phone number", e);
            return null;
        }
    }
}