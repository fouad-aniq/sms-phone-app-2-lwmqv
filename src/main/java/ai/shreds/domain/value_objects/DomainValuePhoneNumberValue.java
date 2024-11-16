package ai.shreds.domain.value_objects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainValuePhoneNumberValue {

    private static final Logger logger = LoggerFactory.getLogger(DomainValuePhoneNumberValue.class);

    private String countryCode;
    private String nationalNumber;
    private Phonenumber.PhoneNumber phoneNumber;

    public DomainValuePhoneNumberValue(String phoneNumberStr) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            this.phoneNumber = phoneUtil.parse(phoneNumberStr, null);
            if (phoneUtil.isValidNumber(this.phoneNumber)) {
                this.countryCode = String.valueOf(this.phoneNumber.getCountryCode());
                this.nationalNumber = String.valueOf(this.phoneNumber.getNationalNumber());
            } else {
                this.countryCode = null;
                this.nationalNumber = null;
            }
        } catch (NumberParseException e) {
            logger.error("Error parsing phone number: " + e.getMessage(), e);
            this.countryCode = null;
            this.nationalNumber = null;
            this.phoneNumber = null;
        }
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