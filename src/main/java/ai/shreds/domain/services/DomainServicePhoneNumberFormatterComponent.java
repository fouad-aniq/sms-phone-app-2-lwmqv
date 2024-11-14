package ai.shreds.domain.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class DomainServicePhoneNumberFormatterComponent {

    private final PhoneNumberUtil phoneNumberUtil;

    public DomainServicePhoneNumberFormatterComponent() {
        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        try {
            PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(parsedNumber);
        } catch (NumberParseException e) {
            // Handle exception appropriately, avoid exposing sensitive information
            return false;
        }
    }

    public String formatPhoneNumber(String phoneNumber) {
        try {
            PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, null);
            if (phoneNumberUtil.isValidNumber(parsedNumber)) {
                // Format the number into E.164 standard international format
                return phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            } else {
                // Handle invalid number case as per error specifications
                return null;
            }
        } catch (NumberParseException e) {
            // Handle exception appropriately, avoid exposing sensitive information
            return null;
        }
    }
}
