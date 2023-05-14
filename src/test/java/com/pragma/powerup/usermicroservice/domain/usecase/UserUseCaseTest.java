package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.exceptions.DomainException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ValidationModelException;
import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.factory.UserTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        //When
        doNothing().when(userPersistencePort).createUser(userModel);
        userUseCase.createUser(userModel);

        //Then
        verify(userPersistencePort).createUser(userModel);
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
    void shouldGetUserById() {
        //Given
        UserModel userModel = UserTestDataFactory.getUserModelWithSetters();

        //When
        when(userPersistencePort.getUserById(1L)).thenReturn(userModel);
        userUseCase.getUserById(1L);

        //Then
        verify(userPersistencePort).getUserById(1L);
        assertThrows(DomainException.class, () -> {
            userUseCase.getUserById(10L);
        });

    }

    @Test
    void shouldGetAllUsers() {
        //Given
        RoleModel roleModel = UserTestDataFactory.getRoleModelWithSetters();
        UserModel userModel1 = UserTestDataFactory.getUserModelWithSetters();
        UserModel userModel2 = UserTestDataFactory.getOtherUserModelWithSetters();

        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(userModel1);
        userModelList.add(userModel2);

        //When
        when(userPersistencePort.getAllUsers(0)).thenReturn(userModelList);
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

        assertThrows(DomainException.class, () -> {
            userUseCase.getAllUsers(10);
        });

        assertEquals(roleModel.getId(), result.get(1).getRoleModel().getId());
        assertEquals(roleModel.getName(), result.get(1).getRoleModel().getName());
        assertEquals(roleModel.getDescription(), result.get(1).getRoleModel().getDescription());

        //Verify
        verify(userPersistencePort).getAllUsers(0);
    }

}