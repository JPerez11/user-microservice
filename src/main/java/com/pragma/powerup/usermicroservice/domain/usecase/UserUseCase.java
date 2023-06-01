package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;

import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.ADMIN_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.CUSTOMER_ROLE_ID;
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
        if (userModel == null) {
            throw new NullPointerException();
        }
        userValidate(userModel);
        if (userPersistencePort.userAlreadyExists(userModel.getDocumentNumber())) {
            throw new UserAlreadyExistsException();
        }
        userModel.setRoleModel(userPersistencePort.getRole());
        if (userModel.getRoleModel().getId().equals(ADMIN_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        userModel.setPassword(userPersistencePort.getPasswordEncrypt(
                userModel.getPassword()
        ));
        userPersistencePort.createUser(userModel);
    }

    @Override
    public List<UserModel> getAllUsers(int page) {
        List<UserModel> list = userPersistencePort.getAllUsers(page);
        if (list.isEmpty()) {
            throw new NoDataFoundException();
        }
        return list;
    }

    @Override
    public UserModel getUserById(Long id) {
        UserModel userModel = userPersistencePort.getUserById(id);
        if (userModel == null) {
            throw new UserNotFoundException();
        }
        return userModel;
    }

    @Override
    public void registerUser(UserModel userModel) {
        if (userModel == null) {
            throw new NullPointerException();
        }
        userValidate(userModel);
        if (userPersistencePort.userAlreadyExists(userModel.getDocumentNumber())) {
            throw new UserAlreadyExistsException();
        }
        if (userPersistencePort.mailAlreadyExists(userModel.getEmail())) {
            throw new MailAlreadyExistsException();
        }
        RoleModel roleModel = new RoleModel();
        roleModel.setId(CUSTOMER_ROLE_ID);
        userModel.setRoleModel(roleModel);
        userModel.setPassword(userPersistencePort.getPasswordEncrypt(userModel.getPassword()));
        userPersistencePort.registerUser(userModel);
    }
}
