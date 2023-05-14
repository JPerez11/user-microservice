package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory.UserControllerTestDataFactory;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.UserHandler;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    @InjectMocks
    UserRestController userRestController;

    @Mock
    UserHandler userHandler;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    void shouldCreateUser() throws Exception {
        //Given
        UserRequestDto userRequestDto =
                UserControllerTestDataFactory.getUserRequest();

        String token = UserControllerTestDataFactory.createJwtToken("ADMIN", "test@example.com");

        // When //Then
        mockMvc.perform(post("/user/")
                        .header("Authorization", "Bearer " + token)
                        .content(UserControllerTestDataFactory
                                .asJsonString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.USER_CREATED_MESSAGE));

    }
    @Test
    void shouldGetUserById() throws Exception {
        //Given
        String token = UserControllerTestDataFactory.createJwtToken("ADMIN", "test@example.com");
        UserResponseDto userResponseDto = UserControllerTestDataFactory.getUserResponse();

        //When //Then
        mockMvc.perform(get("/user/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserControllerTestDataFactory.asJsonString(userResponseDto)))
                .andExpect(status().isOk());

    }
    @Test
    void shouldGetAllUsers() throws Exception {
        //Given
        String token = UserControllerTestDataFactory.createJwtToken("ADMIN", "test@example.com");
        List<UserResponseDto> userList = UserControllerTestDataFactory.getUserResponseList();

        //When //Then
        mockMvc.perform(get("/user/all")
                        .param("page", String.valueOf(0))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserControllerTestDataFactory.asJsonString(userList)))
                .andExpect(status().isOk());

    }

}