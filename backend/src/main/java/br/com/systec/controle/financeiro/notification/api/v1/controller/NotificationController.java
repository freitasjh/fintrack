package br.com.systec.controle.financeiro.notification.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import br.com.systec.controle.financeiro.commons.exception.ValidationError;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.dto.AccountPaymentInputDTO;
import br.com.systec.controle.financeiro.notification.api.v1.dto.NotificationDTO;
import br.com.systec.controle.financeiro.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.controle.financeiro.notification.api.v1.mapper.NotificationMapper;
import br.com.systec.controle.financeiro.notification.model.Notification;
import br.com.systec.controle.financeiro.notification.service.NotificationService;
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
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(RestPath.V1+"/notifications")
@Tag(name = "Notification", description = "Cadastro de Recebidos")
@SecurityRequirement(name = "Authorization")
public class NotificationController extends AbstractController {
    @Autowired
    private NotificationService service;

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastro de notificações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountPaymentInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<NotificationDTO> save(@RequestBody @Valid NotificationInputDTO inputDTO) {
        Notification notification = NotificationMapper.toEntity(inputDTO);

        Notification notificationSaved = service.save(notification);
        messageTemplate.convertAndSend("/topic/notification", "teste");
        return buildSuccessResponse(NotificationMapper.toDTO(notificationSaved));
    }

    @GetMapping(value = "/{userId}")
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

    @PostMapping("/notification/{message}")
    public void sendNotification(@PathVariable("message") String message) {
        messageTemplate.convertAndSend("/user/ajoaohf2@gmail.com/queue/notification", message);
    }
}
