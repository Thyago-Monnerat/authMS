package com.authMS.Auth.microsservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.authMS.Auth.microsservice.exceptions.JwtException;
import com.authMS.Auth.microsservice.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(UserModel user) {
        Algorithm algorithm = Algorithm.HMAC256(key);

        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JwtException("Jwt creation exception " + e.getMessage());
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.of("-03:00"));
    }
}
