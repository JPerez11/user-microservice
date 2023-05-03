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

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^\\d{10,14}$", message = "Document number can only contain numbers")
    private String documentNumber;
    @NotBlank
    @Pattern(regexp = "^\\+?\\d{10,13}$", message = "Wrong phone format")
    private String phoneNumber;
    @NotNull
    @Past(message = "Must be a past date")
    private LocalDate birthdate;
    @NotBlank
    @Email(message = "Must be a well-formed email address")
    private String email;
    @NotBlank
    private String password;

}
