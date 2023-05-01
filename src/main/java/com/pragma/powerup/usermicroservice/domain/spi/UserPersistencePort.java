package com.pragma.powerup.usermicroservice.domain.spi;

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

}
