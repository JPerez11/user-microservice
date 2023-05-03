package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.DomainException;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_PHONE = "^\\+?\\d{10,12}$";
    private static final String REGEX_DOCUMENT_NUMBER = "^\\+?\\d{10,13}$";
    private static final LocalDate CURRENT_DATE = LocalDate.now();

    private UserValidation() {}

    /**
     * Method to validate an instance
     * @param userModel Instance userModel
     */
    public static void userValidate(UserModel userModel) {
        if (emptyStringValidate(userModel.getFirstName())) {
            throw new DomainException("The first name field cannot empty");
        }
        if (emptyStringValidate(userModel.getLastName())) {
            throw new DomainException("The last name field cannot empty");
        }
        if (emptyStringValidate(userModel.getDocumentNumber())) {
            throw new DomainException("The document number field cannot empty");
        } else if (documentNumberValidate(userModel.getDocumentNumber())) {
            throw new DomainException("The document field must be numeric");
        }
        if (emptyStringValidate(userModel.getPhoneNumber())) {
            throw new DomainException("The phone number field cannot empty");
        } else if (phoneNumberValidate(userModel.getPhoneNumber())) {
            throw new DomainException("The phone format is invalid");
        } else if (userModel.getPhoneNumber().length() < 13) {
            throw new DomainException("The content of the phone must contain a maximum of 13 characters");
        }
        if (emptyStringValidate(userModel.getBirthdate().toString())) {
            throw new DomainException("The birthdate field cannot empty");
        } else if (birthdateValidate(userModel.getBirthdate())) {
            throw new DomainException("The user must be of legal age");
        }
        if (emptyStringValidate(userModel.getEmail())) {
            throw new DomainException("The field email cannot empty");
        } else if (emailValidate(userModel.getEmail())) {
            throw new DomainException("The mail format is invalid");
        }
        if (emptyStringValidate(userModel.getPassword())) {
            throw new DomainException("The password field cannot empty");
        }

    }

    /**
     * Method to validate strings
     * @param data The string cannot empty
     */
    private static boolean emptyStringValidate(String data) {
        return data.isEmpty();
    }

    /**
     * Method to validate email
     * @param email The email must be formatted
     */
    private static boolean emailValidate(String email) {
        pattern = Pattern
                .compile(REGEX_EMAIL);
        matcher = pattern.matcher(email);

        return !matcher.find();
    }

    private static boolean documentNumberValidate(String document) {
        pattern = Pattern
                .compile(REGEX_DOCUMENT_NUMBER);
        matcher = pattern.matcher(document);
        return !matcher.find();
    }

    /**
     * Method to validate phone
     * @param phone The phone must be formatted and not exceed the size
     */
    private static boolean phoneNumberValidate(String phone) {
        pattern = Pattern
                .compile(REGEX_PHONE);
        matcher = pattern.matcher(phone);
        return !matcher.find();
    }

    private static boolean birthdateValidate(LocalDate birthdate) {

        Period period = Period.between(birthdate, CURRENT_DATE);

        return period.getYears() < 18;

    }

}
