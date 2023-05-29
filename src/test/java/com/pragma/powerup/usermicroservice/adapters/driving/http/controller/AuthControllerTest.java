package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory.AuthControllerTestDataFactory;
import com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory.UserControllerTestDataFactory;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.LoginRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.AuthHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthHandler authHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    void shouldLoginUser() throws Exception {
        //Given
        LoginRequestDto loginRequestDto =
                AuthControllerTestDataFactory.getLoginRequest();

        //JwtResponseDto jwtResponse = AuthControllerTestDataFactory.getJwtResponse();

        //When

        //Mockito.when(authHandler.login(Mockito.any(LoginRequestDto.class))).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/login")
                .content(AuthControllerTestDataFactory
                        .asJsonString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void shouldRefreshToken() throws Exception {
        // Given
        JwtResponseDto jwtResponseDto = AuthControllerTestDataFactory.getJwtResponse();

        // When/Then
        mockMvc.perform(post("/auth/refresh")
                        .header("Authorization", "Bearer" + jwtResponseDto.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AuthControllerTestDataFactory.asJsonString(jwtResponseDto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        //Given
        UserRequestDto userRequestDto =
                UserControllerTestDataFactory.getUserRequest();

        //When

        mockMvc.perform(post("/auth/register")
                        .content(AuthControllerTestDataFactory
                                .asJsonString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.USER_CREATED_MESSAGE));
    }

}