package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity that represents a user in the database.
 */
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    /** The user's unique identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** The user's first name */
    @Column(name = "first_name", nullable = false)
    private String firstName;
    /** The user's last name */
    @Column(name = "last_name", nullable = false)
    private String lastName;
    /** The user's document number */
    @Column(name = "document_number", length = 20, unique = true, nullable = false)
    private String documentNumber;
    /** The user's phone number */
    @Column(name = "phone_number", length = 13, nullable = false)
    private String phoneNumber;
    /** The user's birthdate */
    @Column(nullable = false)
    private LocalDate birthdate;
    @Column(nullable = false, unique = true)
    /** The user's email address */
    private String email;
    /** The user's password */
    @Column(nullable = false)
    private String password;
    /** The user's role */
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleEntity roleEntity;
}
