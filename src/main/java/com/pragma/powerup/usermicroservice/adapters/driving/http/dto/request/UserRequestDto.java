package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @Author Jhoan Perez
 * DTO class for user request
 * Use the lombok library to create getter, setter and constructors methods
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "First name must not be empty")
    private String firstName;
    @NotBlank(message = "Last name must not be empty")
    private String lastName;
    @NotBlank(message = "DNI must not be empty")
    @Pattern(regexp = "^\\d{9,13}$", message = "Document number can only contain numbers")
    private String documentNumber;
    @NotBlank(message = "Phone number must not be empty")
    @Pattern(regexp = "^\\+?\\d{9,12}$", message = "Wrong phone format")
    private String phoneNumber;
    @NotNull(message = "Birthdate must not be empty")
    @Past(message = "Must be a past date")
    private LocalDate birthdate;
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Must be a well-formed email address")
    private String email;
    @NotBlank(message = "Password must not be empty")
    private String password;

}
