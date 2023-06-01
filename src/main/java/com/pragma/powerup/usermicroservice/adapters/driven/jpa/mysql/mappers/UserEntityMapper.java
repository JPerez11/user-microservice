package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    @Mapping(target = "roleEntity", source = "roleModel")
    UserEntity toUserEntity(UserModel userModel);

    @Mapping(target = "roleModel", source = "roleEntity")
    UserModel toUserModel(UserEntity userEntity);

    List<UserModel> toUserModelList(List<UserEntity> userEntityList);

}
