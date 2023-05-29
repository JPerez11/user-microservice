package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.LoginRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.AuthHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.UserRequestMapper;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtToken;
import com.pragma.powerup.usermicroservice.domain.api.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthHandlerImpl implements AuthHandler {

    private final AuthenticationManager authenticationManager;
    private final UserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;

    @Override
    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = JwtToken.generateToken(userDetails);
        return new JwtResponseDto(jwt);
    }

    @Override
    public JwtResponseDto refresh(JwtResponseDto jwtResponseDto) throws ParseException {
        String token = JwtToken.refreshToken(jwtResponseDto);
        return new JwtResponseDto(token);
    }

    @Override
    public void register(UserRequestDto userRegister) {
        userServicePort.registerUser(userRequestMapper.toUserModel(userRegister));
    }
}
