package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndRoleEntityId(Long idUser, Long idRole);

    Optional<UserEntity> findByDocumentNumber(String documentNumber);

    Optional<UserEntity> findByEmail(String email);
}
