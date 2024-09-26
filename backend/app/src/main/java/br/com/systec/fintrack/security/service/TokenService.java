package br.com.systec.fintrack.security.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.login.api.v1.dto.LoginResponseDTO;
import br.com.systec.fintrack.security.exceptions.SecurityTokenExpiredException;
import br.com.systec.fintrack.user.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.StreamSupport;

@Service
public class TokenService {
    private static String ISSUE = "Systec-agendamento.api";
    @Value("${api.security.token.secret}")
    private String secret;


    public LoginResponseDTO generatedToken(Authentication authentication) throws JWTCreationException {

        User user = (User) authentication.getPrincipal();

        String token = JWT.create()
                .withIssuer(ISSUE)
                .withSubject(user.getUsername())
                .withClaim("tenant", user.getTenantId())
                .withClaim("userId", user.getId())
                .withExpiresAt(generateDateExpired())
                .sign(getAlgorithm());
        LoginResponseDTO loginResponse = new LoginResponseDTO(token, "Bearer");
        loginResponse.setUserId(user.getId());
        String profile = StreamSupport.stream(user.getProfile().spliterator(), false).toList().get(0).getName();
        loginResponse.setProfile(profile);

        return loginResponse;
    }

    public String getSubject(String tokenJWT) throws SecurityTokenExpiredException {
        try {
            JWT.require(getAlgorithm()).build().verify(tokenJWT);
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUE)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (TokenExpiredException e){
            throw new SecurityTokenExpiredException();
        }catch (Exception e){
            throw new BaseException(e.getMessage(), e);
        }
    }

    public Long getTenant(String token) {
        return JWT.require(getAlgorithm())
                .withIssuer(ISSUE)
                .build()
                .verify(token)
                .getClaim("tenant").asLong();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant generateDateExpired() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }
}