package com.authMS.Auth.microsservice.adapters.outbound.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.authMS.Auth.microsservice.domain.security.IJwtService;
import com.authMS.Auth.microsservice.infrastructure.exceptions.JwtException;
import com.authMS.Auth.microsservice.domain.user.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public String generateToken(UserModel user) {
        Algorithm algorithm = Algorithm.HMAC256(key);

        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().toString())
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
