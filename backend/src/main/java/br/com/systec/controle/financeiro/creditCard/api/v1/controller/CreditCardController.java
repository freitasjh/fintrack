package br.com.systec.controle.financeiro.creditCard.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import br.com.systec.controle.financeiro.commons.exception.ValidationError;
import br.com.systec.controle.financeiro.creditCard.api.v1.dto.CreditCardDTO;
import br.com.systec.controle.financeiro.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.controle.financeiro.creditCard.api.v1.mapper.CreditCardMapper;
import br.com.systec.controle.financeiro.creditCard.filter.CreditCardFilterVO;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;

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

    @PutMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualiza as informações do cartão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardInputDTO> update(@RequestBody @Valid CreditCardInputDTO inputDTO, @PathVariable("id") Long id) {
        CreditCard creditCard = CreditCardMapper.toEntity(inputDTO);

        CreditCard creditCardSaved = service.update(creditCard, id);

        return buildSuccessResponse(CreditCardMapper.toInputDTO(creditCardSaved));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Pesquisa cartões de credito por filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Page<CreditCardDTO>> findByFilter(@RequestParam(value = "keyword", required = false) String keyword,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                            @RequestParam(value = "accountId", required = false) Long accountId) {
        CreditCardFilterVO filterVO = new CreditCardFilterVO(limit, page, keyword);
        filterVO.setBankAccountId(accountId);

        Page<CreditCard> pageOfFilter = service.findByFilter(filterVO);

        return buildSuccessResponse(CreditCardMapper.toPageDTO(pageOfFilter));
    }
}
