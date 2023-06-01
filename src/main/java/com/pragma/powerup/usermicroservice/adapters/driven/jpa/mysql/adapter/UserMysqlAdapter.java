package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RoleEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.UserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RoleRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.UserRepository;
import com.pragma.powerup.usermicroservice.domain.model.RoleModel;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.actions.RoleAuthentication.getRoleWithAuthentication;
import static com.pragma.powerup.usermicroservice.configuration.Constants.ADMIN_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.CUSTOMER_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.MAX_PAGE_SIZE;

@RequiredArgsConstructor
@Transactional
public class UserMysqlAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityMapper roleEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserModel userModel) {
        userRepository.save(userEntityMapper.toUserEntity(userModel));
    }

    @Override
    public List<UserModel> getAllUsers(int page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        Page<UserEntity> userEntityList = userRepository.findAll(pagination);
        if (userEntityList.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userEntityMapper.toUserModelList(userEntityList.getContent());
    }

    @Override
    public UserModel getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public void registerUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toUserEntity(userModel);
        if (userRepository.findByDocumentNumber(userEntity.getDocumentNumber()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        RoleEntity role = new RoleEntity();
        role.setId(CUSTOMER_ROLE_ID);
        userEntity.setRoleEntity(role);

        if (userEntity.getRoleEntity().getId().equals(ADMIN_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public boolean userAlreadyExists(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public RoleModel getRole() {
        return roleEntityMapper.toRoleModel(getRoleWithAuthentication(roleRepository));
    }

    @Override
    public String getPasswordEncrypt(String password) {
        return passwordEncoder.encode(password);
    }

}
