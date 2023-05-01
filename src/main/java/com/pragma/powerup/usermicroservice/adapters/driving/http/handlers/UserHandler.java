package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;

import java.util.List;

public interface UserHandler {

    void createUser(UserRequestDto userRequestDto);
    List<UserResponseDto> getAllUsers(int page);
    UserResponseDto getUserById(Long id);

}
