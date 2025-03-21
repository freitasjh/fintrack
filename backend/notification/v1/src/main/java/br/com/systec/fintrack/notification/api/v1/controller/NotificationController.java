package br.com.systec.fintrack.notification.api.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
import br.com.systec.fintrack.notification.api.v1.converter.NotificationMapper;
import br.com.systec.fintrack.notification.api.v1.dto.NotificationResponseDTO;
import br.com.systec.fintrack.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(RestPath.V1+"/notifications")
@Tag(name = "Notification", description = "Cadastro de Recebidos")
@SecurityRequirement(name = "Authorization")
public class NotificationController extends AbstractController {
    @Autowired
    private NotificationService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastro de notificações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotificationInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<NotificationResponseDTO> save(@RequestBody @Valid NotificationInputDTO inputDTO) {
        Notification notification = NotificationMapper.toEntity(inputDTO);

        Notification notificationSaved = service.save(notification);
        return buildSuccessResponse(NotificationMapper.toResponseDTO(notificationSaved));
    }

    @GetMapping(value = "/count/{userId}")
    @Operation(description = "Retorna o total de notificações não visualizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Long> findTotalNotificationNotVisualized(@PathVariable("userId") Long userId) {
        Long total = service.findTotalNotificationByUserId(userId);

        return buildSuccessResponse(total);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna a lista de notificações não visualizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotificationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationNotVisualized(@PathVariable("userId") Long userId) {
        List<Notification> notifications = service.findByTenantAndUserIdAndNotVisualized(userId);

        return buildSuccessResponse(NotificationMapper.toListResponseDTO(notifications));
    }
}
