package br.com.systec.fintrack.login.api.v1;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.login.api.v1.dto.LoginDTO;
import br.com.systec.fintrack.login.api.v1.dto.LoginResponseDTO;
import br.com.systec.fintrack.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(RestPath.V1+"/login")
@Tag(name = "Login", description = "Realiza a authenticação do usuario")
public class LoginController extends AbstractController {

    @Autowired
    private LoginService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Realiza a authenticação do usuario e retorna o Token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token de acesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponseDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = service.login(loginDTO);

        return buildSuccessResponse(loginResponseDTO);
    }
}
