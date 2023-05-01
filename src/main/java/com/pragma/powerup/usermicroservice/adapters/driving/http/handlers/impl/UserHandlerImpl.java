package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.UserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.UserRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.UserResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @Override
    public void createUser(UserRequestDto userRequestDto) {
        userServicePort.createUser(userRequestMapper.toUserModel(userRequestDto));
    }

    @Override
    public List<UserResponseDto> getAllUsers(int page) {
        return userResponseMapper.toResponseDtoList(userServicePort.getAllUsers(page));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userResponseMapper.toUserResponseDto(userServicePort.getUserById(id));
    }
}
