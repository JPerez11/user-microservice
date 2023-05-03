package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.actions.validations;

import java.time.LocalDate;
import java.time.Period;

public class ValidateAge {

    private static final LocalDate dateNow = LocalDate.now();

    private ValidateAge() {}

    public static boolean validateBirthDate(LocalDate birthdate) {

        Period period = Period.between(birthdate, dateNow);

        return period.getYears() < 18;

    }

}
