package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private String documentNumber;
    @NotBlank @Pattern(regexp = "^\\+?\\d+$", message = "Wrong phone format")
    private String phoneNumber;
    @NotBlank @Past
    private LocalDate birthdate;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;

}
