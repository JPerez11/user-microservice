package com.pragma.powerup.usermicroservice.domain.usecase.factory;

import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.time.LocalDate;

public class UserTestDataFactory {

    public static RoleModel getRoleModelWithSetters() {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(1L);
        roleModel.setRoleName("ADMIN");
        roleModel.setDescription("PLACE MANAGER");
        return  roleModel;
    }
    public static UserModel getUserModelWithSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Admin");
        userModel.setLastName("Admin");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("admin@gmail.com");
        userModel.setPassword("admin123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));
        userModel.setRoleModel(getRoleModelWithSetters());

        return userModel;
    }

    public static RoleModel getRoleModel() {
        return new RoleModel(1L, "ADMIN", "PLACE MANAGER");
    }

    public static UserModel getUserModelEmpty() {
        return new UserModel(1L,
                "",
                "",
                "",
                "",
                LocalDate.of(2000, 1, 1),
                "test@.com",
                "",
                getRoleModel());
    }

    public static UserModel getOtherUserModelWithSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(2L);
        userModel.setFirstName("Name");
        userModel.setLastName("LastName");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("test2@gmail.com");
        userModel.setPassword("test123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));
        userModel.setRoleModel(getRoleModelWithSetters());

        return userModel;
    }

}
