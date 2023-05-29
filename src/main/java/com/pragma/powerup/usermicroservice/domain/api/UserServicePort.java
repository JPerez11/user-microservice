package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.util.List;

/**
 * @Author Jhoan Perez
 * User service interface for actions
 */
public interface UserServicePort {

    // Method to create a new user
    void createUser(UserModel userModel);
    // Method to list all users with pagination
    List<UserModel> getAllUsers(int page);
    // Method to get one user by id
    UserModel getUserById(Long id);
    //Method to register a new customer
    void registerUser(UserModel userModel);
}
