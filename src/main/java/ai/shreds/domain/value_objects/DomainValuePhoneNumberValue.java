package ai.shreds.domain.value_objects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class DomainValuePhoneNumberValue {

    private final String countryCode;
    private final String nationalNumber;
    private final Phonenumber.PhoneNumber phoneNumber;

    public DomainValuePhoneNumberValue(String phoneNumberStr) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String tempCountryCode = null;
        String tempNationalNumber = null;
        Phonenumber.PhoneNumber tempPhoneNumber = null;
        try {
            tempPhoneNumber = phoneUtil.parse(phoneNumberStr, null);
            if (phoneUtil.isValidNumber(tempPhoneNumber)) {
                tempCountryCode = String.valueOf(tempPhoneNumber.getCountryCode());
                tempNationalNumber = String.valueOf(tempPhoneNumber.getNationalNumber());
            }
        } catch (NumberParseException e) {
            // Handle exception accordingly (e.g., log error)
        }
        this.countryCode = tempCountryCode;
        this.nationalNumber = tempNationalNumber;
        this.phoneNumber = tempPhoneNumber;
    }

    public boolean isValid() {
        return this.countryCode != null && this.nationalNumber != null;
    }

    public String formatPhoneNumber() {
        if (isValid()) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            return phoneUtil.format(this.phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } else {
            throw new IllegalStateException("Invalid phone number");
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }
}
