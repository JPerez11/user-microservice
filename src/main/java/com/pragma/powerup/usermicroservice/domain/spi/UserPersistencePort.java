package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.util.List;

/**
 * @Author Jhoan Perez
 * User persistence interface for data processing
 */
public interface UserPersistencePort {

    // Method to create a new user
    void createUser(UserModel userModel);
    // Method to list all users with pagination
    List<UserModel> getAllUsers(int page);
    // Method to get one user by id
    UserModel getUserById(Long id);
    // Method to register a new customer
    void registerUser(UserModel userModel);
    // Method to valid exception
    boolean userAlreadyExists(String documentNumber);
    boolean mailAlreadyExists(String email);
    // Method to get role
    RoleModel getRole();
    // Method to get password encrypt
    String getPasswordEncrypt(String password);
}
