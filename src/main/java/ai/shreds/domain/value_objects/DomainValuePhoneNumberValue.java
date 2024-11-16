package ai.shreds.domain.value_objects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class DomainValuePhoneNumberValue {
    private final String countryCode;
    private final String nationalNumber;

    public DomainValuePhoneNumberValue(String countryCode, String nationalNumber) {
        this.countryCode = countryCode;
        this.nationalNumber = nationalNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public boolean isValid() {
        String fullNumber = "+" + countryCode + nationalNumber;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber phoneNumberProto = phoneUtil.parse(fullNumber, null);
            boolean isPossibleNumber = phoneUtil.isPossibleNumber(phoneNumberProto);
            boolean isValidNumber = phoneUtil.isValidNumber(phoneNumberProto);
            return isPossibleNumber && isValidNumber;
        } catch (NumberParseException e) {
            return false;
        }
    }
}
