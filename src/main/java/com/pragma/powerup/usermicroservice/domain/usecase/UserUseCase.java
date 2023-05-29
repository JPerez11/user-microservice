package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DomainException;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;

import java.util.List;

import static com.pragma.powerup.usermicroservice.domain.validations.UserValidation.userValidate;

/**
 * @Author Jhoan Perez
 * User Use Cases
 */
public class UserUseCase implements UserServicePort {

    // Persistence injection
    private final UserPersistencePort userPersistencePort;

    // Constructor to inject persistence
    public UserUseCase(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void createUser(UserModel userModel) {
        userValidate(userModel);
        userPersistencePort.createUser(userModel);
    }

    @Override
    public List<UserModel> getAllUsers(int page) {
        List<UserModel> userModelList = userPersistencePort.getAllUsers(page);
        if (userModelList.isEmpty()) {
            throw new DomainException("Empty list");
        }
        return userModelList;
    }

    @Override
    public UserModel getUserById(Long id) {
        UserModel userModel = userPersistencePort.getUserById(id);
        if (userModel == null) {
            throw new DomainException("Empty list");
        }
        return userModel;
    }

    @Override
    public void registerUser(UserModel userModel) {
        userValidate(userModel);
        userPersistencePort.registerUser(userModel);
    }
}
