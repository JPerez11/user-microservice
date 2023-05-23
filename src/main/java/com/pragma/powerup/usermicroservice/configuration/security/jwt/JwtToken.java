package com.pragma.powerup.usermicroservice.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.UserDetailsImpl;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class to create the token and extract its data.
 */
public class JwtToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtToken.class);
    private static final String ROLE = "role";
    private static final String ID_USER = "id";
    private static final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXvCJ9";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 3600L;

    private JwtToken () {}

    public static String generateToken(UserDetails userDetails) {
        //Token expiration time
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000L;
        //Token expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        //User email
        String email = userDetails.getUsername();
        //User role
        String role = userDetails
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found role for this user."))
                .getAuthority();

        Long id = ((UserDetailsImpl) userDetails).getUserId();

        //Set subject email in JWT
        Claims claims = Jwts.claims().setSubject(email);
        //Set the role in JWT
        claims.put(ROLE, role);
        claims.put(ID_USER, id);

        //Token generation and return
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    /**
     * Method to obtain token credentials.
     * @param token JWT
     * @return User authentication
     */
    public static UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            //Extract the email from the token
            String email = claims.getSubject();
            //Extract the role from the token
            String role = (String) claims.get(ROLE);
            //Create an Authorities with the role
            Collection<? extends GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(role));
            //Return a new User authentication with user credentials
            return new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    authorities
            );
        }
        catch (JwtException e) {
            return null;
        }
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("Malformed token");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported token");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired token");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Empty token");
        } catch (SignatureException e) {
            LOGGER.error("Signature failure");
        }
        return false;
    }

    public static String refreshToken(JwtResponseDto jwtResponseDto) throws ParseException {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(jwtResponseDto.getToken());
        } catch (ExpiredJwtException e) {
            JWT jwt = JWTParser.parse(jwtResponseDto.getToken());
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String nombreUsuario = claims.getSubject();
            List<String> roles = claims.getStringListClaim(ROLE);

            return Jwts.builder()
                    .setSubject(nombreUsuario)
                    .claim(ROLE, roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_VALIDITY_SECONDS))
                    .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                    .compact();
        }
        return null;
    }

}
