package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.UserMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RoleEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.UserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RoleRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.UserRepository;
import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import com.pragma.powerup.usermicroservice.domain.spi.UserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleEntityMapper roleEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }
    @Bean
    public UserPersistencePort userPersistencePort() {
        return new UserMysqlAdapter(userRepository, roleRepository, userEntityMapper,
                roleEntityMapper, passwordEncoder);
    }
}
