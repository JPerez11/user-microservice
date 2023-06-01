package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.DomainException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.UserTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserUseCaseTest {

    @Mock
    UserPersistencePort userPersistencePort;

    @InjectMocks
    UserUseCase userUseCase;

    @Test
    void shouldCreateUser() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();
        RoleModel roleModel = UserTestDataFactory.getRoleOwnerWithSetters();
        String passwordEncrypted = "$2a$10$G.2DHaPhPXfVzh/bn71KruzG/13XPjfwP6pRVOjeBGGCAEL0CU51W";

        //When
        Mockito.when(userPersistencePort.userAlreadyExists(userModel.getDocumentNumber()))
                .thenReturn(false);
        Mockito.when(userPersistencePort.getRole()).thenReturn(roleModel);
        userModel.setRoleModel(roleModel);
        Mockito.when(userPersistencePort.getPasswordEncrypt(userModel.getPassword()))
                .thenReturn(passwordEncrypted);
        userModel.setPassword(passwordEncrypted);
        doNothing().when(userPersistencePort).createUser(userModel);
        userUseCase.createUser(userModel);

        //Then
        verify(userPersistencePort).createUser(userModel);
    }

    @Test
    void shouldThrowNullPointerException() {
        //Then
        assertThrows(NullPointerException.class, () -> {
            userUseCase.createUser(null);
        });
    }

    @Test
    void shouldThrowValidationModelException() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelEmpty();

        //When

        //Then
        assertThrows(ValidationModelException.class, () -> {
            userUseCase.createUser(userModel);
        });
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();

        //When
        Mockito.when(userPersistencePort.userAlreadyExists(userModel.getDocumentNumber()))
                .thenReturn(true);

        //Then
        assertThrows(UserAlreadyExistsException.class, () -> {
            userUseCase.createUser(userModel);
        });
    }

    @Test
    void shouldThrowRoleNotAllowedForCreationException() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();
        RoleModel roleModel = UserTestDataFactory.getRoleAdminWithSetters();

        //When
        Mockito.when(userPersistencePort.userAlreadyExists(userModel.getDocumentNumber()))
                .thenReturn(false);
        Mockito.when(userPersistencePort.getRole()).thenReturn(roleModel);
        userModel.setRoleModel(roleModel);

        //Then
        assertThrows(RoleNotAllowedForCreationException.class, () -> {
            userUseCase.createUser(userModel);
        });
    }

    @Test
    void shouldGetAllUsers() {
        //Given
        RoleModel roleModel = UserTestDataFactory.getRoleAdminWithSetters();
        UserModel userModel1 = UserTestDataFactory.getUserModelWithSetters();
        UserModel userModel2 = UserTestDataFactory.getOtherUserModelWithSetters();

        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(userModel1);
        userModelList.add(userModel2);

        //When
        Mockito.when(userPersistencePort.getAllUsers(0))
                .thenReturn(userModelList);
        List<UserModel> result = userUseCase.getAllUsers(0);

        //Then
        assertEquals(userModelList, result);
        assertEquals(userModelList.get(0).getId(), result.get(0).getId());
        assertEquals(userModelList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(userModelList.get(0).getLastName(), result.get(0).getLastName());
        assertEquals(userModelList.get(0).getDocumentNumber(), result.get(0).getDocumentNumber());
        assertEquals(userModelList.get(0).getPhoneNumber(), result.get(0).getPhoneNumber());
        assertEquals(userModelList.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(userModelList.get(0).getPassword(), result.get(0).getPassword());
        assertEquals(userModelList.get(0).getRoleModel(), result.get(0).getRoleModel());

        assertEquals(roleModel.getId(), result.get(1).getRoleModel().getId());
        assertEquals(roleModel.getName(), result.get(1).getRoleModel().getName());
        assertEquals(roleModel.getDescription(), result.get(1).getRoleModel().getDescription());

        //Verify
        Mockito.verify(userPersistencePort).getAllUsers(0);
    }

    @Test
    void shouldThrowNoDataFoundException() {
        //When
        Mockito.when(userPersistencePort.getAllUsers(10))
                .thenReturn(Collections.emptyList());

        //Then
        assertThrows(NoDataFoundException.class, () -> {
            userUseCase.getAllUsers(10);
        });
    }

    @Test
    void shouldGetUserById() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();

        //When
        Mockito.when(userPersistencePort.getUserById(1L)).thenReturn(userModel);
        userUseCase.getUserById(1L);

        //Then
        Mockito.verify(userPersistencePort).getUserById(1L);

    }

    @Test
    void shouldThrowUserNotFoundException() {
        // When
        Mockito.when(userPersistencePort.getUserById(1L)).thenReturn(null);

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            userUseCase.getUserById(1L);
        });
    }

    @Test
    void shouldRegisterUser() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();

        //When
        doNothing().when(userPersistencePort).registerUser(userModel);
        userUseCase.registerUser(userModel);

        //Then
        verify(userPersistencePort).registerUser(userModel);
    }

}