package br.com.systec.fintrack.administrator.user.v1;

import br.com.systec.fintrack.administrator.user.model.User;
import br.com.systec.fintrack.administrator.user.service.UserService;
import br.com.systec.fintrack.administrator.user.v1.converter.UserConverter;
import br.com.systec.fintrack.administrator.user.v1.dto.UserInputDTO;
import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(RestPath.V1+"/users")
@Tag(name = "/users", description = "Cadastros de usuarios")
public class UserController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/users";
    @Autowired
    private UserService userService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Cadastrar um novo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<UserInputDTO> save(@RequestBody @Valid UserInputDTO userInputDTO, UriComponentsBuilder uriBuilder) {
        UserInputDTO userSavedDTO = saveUser(userInputDTO);

        return buildSuccessResponseCreated(userSavedDTO, uriBuilder, ENDPOINT, userSavedDTO.getId());
    }

    @PostMapping(value = "/newAccount",produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Cadastrar uma nova conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<UserInputDTO> saveNewAccount(@RequestBody @Valid UserInputDTO userInputDTO, UriComponentsBuilder uriBuilder) {
        userInputDTO.setUserPrincipalTenant(true);
        UserInputDTO userSavedDTO = saveUser(userInputDTO);

        return buildSuccessResponseCreated(userSavedDTO, uriBuilder, ENDPOINT, userSavedDTO.getId());
    }

    private UserInputDTO saveUser(UserInputDTO userInputDTO) {
        User user = UserConverter.getInstance().toEntity(userInputDTO);

        User userSaved = userService.save(user);

        return UserConverter.getInstance().toInputDTO(userSaved);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Atualizar informações usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<UserInputDTO> update(@RequestBody UserInputDTO userInputDTO) throws BaseException{
        User user = UserConverter.getInstance().toEntity(userInputDTO);

        User userUpdated = userService.update(user);

        UserInputDTO userUpdateDTO = UserConverter.getInstance().toInputDTO(userUpdated);

        return buildSuccessResponse(userUpdateDTO);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInputDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuario não encontrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<UserInputDTO> findById(@PathVariable("userId") Long userId) throws BaseException {
        User user = userService.findById(userId);

        UserInputDTO userReturnedDTO = UserConverter.getInstance().toInputDTO(user);

        return buildSuccessResponse(userReturnedDTO);
    }
}
