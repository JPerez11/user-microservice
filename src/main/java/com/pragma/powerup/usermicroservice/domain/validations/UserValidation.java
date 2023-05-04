package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    private static Pattern pattern;
    private static Matcher matcher;
    private static Map<String, String> exceptionMap;
    private static final String EMPTY_FIELD = "This field cannot empty";
    private static final String FORMAT_INVALID = "The format is invalid";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String REGEX_PHONE_NUMBER = "^\\+?\\d{9,12}$";
    private static final String REGEX_DOCUMENT_NUMBER = "^\\d{9,13}$";
    private static final LocalDate CURRENT_DATE = LocalDate.now();

    private UserValidation() {}

    /**
     * Method to validate an instance
     * @param userModel Instance userModel
     */
    public static void userValidate(UserModel userModel) {
        exceptionMap = new HashMap<>();
        // Validate data if empty
        validateField(userModel.getFirstName(), "first name", EMPTY_FIELD, UserValidation::isStringEmpty);
        validateField(userModel.getLastName(), "last name", EMPTY_FIELD, UserValidation::isStringEmpty);
        validateField(userModel.getDocumentNumber(), "document", EMPTY_FIELD, UserValidation::isStringEmpty);
        validateField(userModel.getPhoneNumber(), "phone", EMPTY_FIELD, UserValidation::isStringEmpty);
        validateField(userModel.getBirthdate().toString(), "birthdate", EMPTY_FIELD,
                UserValidation::isStringEmpty);
        validateField(userModel.getPassword(), "password", EMPTY_FIELD, UserValidation::isStringEmpty);
        validateField(userModel.getEmail(), "email", EMPTY_FIELD, UserValidation::isStringEmpty);
        //Validate data if badly formatted
        validateField(userModel.getDocumentNumber(), "document format", FORMAT_INVALID,
                UserValidation::isDocumentValid);
        validateField(userModel.getPhoneNumber(), "phone format", FORMAT_INVALID, UserValidation::isPhoneValid);
        validateField(userModel.getBirthdate().toString(), "birthdate",
                "The user cannot be a minor", UserValidation::isBirthdateValid);
        validateField(userModel.getEmail(), "email format", FORMAT_INVALID, UserValidation::isEmailValid);

        if (!exceptionMap.isEmpty()) {
            throw new ValidationModelException(exceptionMap);
        }

    }

    private static void validateField(String field, String fieldName,
                                      String errorMessage, Predicate<String> validator) {
        if(validator.test(field)) {
            exceptionMap.put(fieldName, String.format(errorMessage));
        }
    }

    /**
     * Method to validate strings
     * @param data The string cannot empty
     * @return true if string is empty
     */
    private static boolean isStringEmpty(String data) {
        return data.trim().isEmpty();
    }

    /**
     * Method to validate email
     * @param email The email must be formatted
     * @return true if format is invalid
     */
    private static boolean isEmailValid(String email) {
        pattern = Pattern
                .compile(REGEX_EMAIL);
        matcher = pattern.matcher(email);
        return !matcher.find();
    }

    /**
     * Method to validate document
     * @param document The document must be formatted
     * @return true if format is invalid
     */
    private static boolean isDocumentValid(String document) {
        pattern = Pattern
                .compile(REGEX_DOCUMENT_NUMBER);
        matcher = pattern.matcher(document);
        return !matcher.find();
    }

    /**
     * Method to validate phone
     * @param phone The phone must be formatted and not exceed the size
     * @return true if format is invalid
     */
    private static boolean isPhoneValid(String phone) {
        pattern = Pattern
                .compile(REGEX_PHONE_NUMBER);
        matcher = pattern.matcher(phone);
        return !matcher.find();
    }

    /**
     * Method to validate birthdate
     * @param birthdate Age must be over 18 years old
     * @return true if age is less than 18
     */
    private static boolean isBirthdateValid(String birthdate) {
        LocalDate date = LocalDate.parse(birthdate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(date, CURRENT_DATE);
        return period.getYears() < 18;
    }

}
