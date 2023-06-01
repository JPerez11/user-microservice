package com.pragma.powerup.usermicroservice.domain.usecase.factory;

import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;

import java.time.LocalDate;

public class UserTestDataFactory {

    public static RoleModel getRoleAdminWithSetters() {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(1L);
        roleModel.setName("ADMIN");
        roleModel.setDescription("PLACE MANAGER");
        return  roleModel;
    }

    public static RoleModel getRoleOwnerWithSetters() {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(2L);
        roleModel.setName("OWNER");
        roleModel.setDescription("OWNER_ROLE");
        return  roleModel;
    }
    public static RoleModel getRoleCustomerWithSetters() {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(4L);
        roleModel.setName("CUSTOMER");
        roleModel.setDescription("CUSTOMER_ROLE");
        return  roleModel;
    }

    public static UserModel getUserAdminWithSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Admin");
        userModel.setLastName("Admin");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("admin@gmail.com");
        userModel.setPassword("admin123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));

        return userModel;
    }

    public static UserModel getUserOwnerWithSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Owner");
        userModel.setLastName("Owner");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("owner@gmail.com");
        userModel.setPassword("owner123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));

        return userModel;
    }

    public static UserModel getUserCustomerWithSetters() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setFirstName("Customer");
        userModel.setLastName("Customer");
        userModel.setDocumentNumber("1234567890");
        userModel.setPhoneNumber("+571234567890");
        userModel.setEmail("customer@gmail.com");
        userModel.setPassword("customer123");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));

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
        userModel.setRoleModel(getRoleAdminWithSetters());

        return userModel;
    }

}
