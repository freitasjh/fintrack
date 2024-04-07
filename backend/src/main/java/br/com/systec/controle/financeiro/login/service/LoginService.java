package br.com.systec.controle.financeiro.login.service;

import br.com.systec.controle.financeiro.commons.exception.LoginException;
import br.com.systec.controle.financeiro.login.api.v1.dto.LoginDTO;
import br.com.systec.controle.financeiro.login.api.v1.dto.LoginResponseDTO;
import br.com.systec.controle.financeiro.security.service.AuthenticationService;
import br.com.systec.controle.financeiro.security.service.TokenService;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public LoginResponseDTO login(LoginDTO loginDTO) {
        try {
            var userAndPasswordToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            var authenticate = manager.authenticate(userAndPasswordToken);

            LoginResponseDTO loginResponseDTO = tokenService.generatedToken(authenticate);

            return loginResponseDTO;

        } catch (JWTCreationException e) {
            throw new LoginException("Ocorreu um erro ao tentar gerar o token de acesso", e);
        } catch (AuthenticationException e) {
            throw new LoginException(e.getMessage(), e);
        }
    }

}
