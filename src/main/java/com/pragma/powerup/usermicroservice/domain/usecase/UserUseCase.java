package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;

import java.util.List;

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
        userPersistencePort.createUser(userModel);
    }

    @Override
    public List<UserModel> getAllUsers(int page) {
        List<UserModel> userModelList = userPersistencePort.getAllUsers(page);
        if (userModelList.isEmpty()) {
            throw new NoDataFoundException("Empty list");
        }
        return userModelList;
    }

    @Override
    public UserModel getUserById(Long id) {
        UserModel userModel = userPersistencePort.getUserById(id);
        if (userModel == null) {
            throw new NoDataFoundException("Empty list");
        }
        return userModel;
    }
}
