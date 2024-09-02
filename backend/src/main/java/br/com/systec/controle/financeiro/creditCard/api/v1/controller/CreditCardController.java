package br.com.systec.controle.financeiro.creditCard.api.v1.controller;

import br.com.systec.controle.financeiro.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import br.com.systec.controle.financeiro.commons.exception.ValidationError;
import br.com.systec.controle.financeiro.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.controle.financeiro.creditCard.api.v1.mapper.CreditCardMapper;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.service.CreditCardService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(RestPath.V1+"/credit-cards")
@Tag(name = "Credit card", description = "Cadastro de cartão de credito")
@SecurityRequirement(name = "Authorization")
public class CreditCardController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/credit-cards";
    @Autowired
    private CreditCardService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastra novo cartão de credito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardInputDTO> save(@RequestBody @Valid CreditCardInputDTO inputDTO,
                                                   UriComponentsBuilder builder) {
        CreditCard creditCard = CreditCardMapper.toEntity(inputDTO);

        CreditCard creditCardSaved = service.save(creditCard);

        return buildSuccessResponseCreated(CreditCardMapper.toInputDTO(creditCardSaved), builder, ENDPOINT, creditCardSaved.getId());
    }
}
