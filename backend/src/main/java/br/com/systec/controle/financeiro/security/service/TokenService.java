package br.com.systec.controle.financeiro.security.service;

import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.login.api.v1.dto.LoginResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
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

    public String getSubject(String tokenJWT) {
        JWT.require(getAlgorithm()).build().verify(tokenJWT);
        return JWT.require(getAlgorithm())
                .withIssuer(ISSUE)
                .build()
                .verify(tokenJWT)
                .getSubject();
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