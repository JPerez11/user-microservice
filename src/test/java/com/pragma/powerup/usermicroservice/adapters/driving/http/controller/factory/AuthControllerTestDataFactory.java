package com.pragma.powerup.usermicroservice.adapters.driving.http.controller.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.LoginRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class AuthControllerTestDataFactory {

    private static final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9";

    private AuthControllerTestDataFactory() {}

    public static LoginRequestDto getLoginRequest() {
        return new LoginRequestDto("test@example.com", "samplePassword");
    }

    public static JwtResponseDto getJwtResponse() {
        return new JwtResponseDto(createJwtToken("ADMIN", getLoginRequest().getEmail()));
    }

    public static String createJwtToken(String role, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", role);
        //Token expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + 3_600_000);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return objectMapper.writeValueAsString(object);
    }
}
