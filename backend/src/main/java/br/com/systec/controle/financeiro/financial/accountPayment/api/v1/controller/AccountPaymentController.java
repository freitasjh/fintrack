package br.com.systec.controle.financeiro.financial.accountPayment.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import br.com.systec.controle.financeiro.commons.exception.ValidationError;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.dto.AccountPaymentDTO;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.dto.AccountPaymentInputDTO;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.mapper.AccountPaymentMapper;
import br.com.systec.controle.financeiro.financial.accountPayment.filter.AccountPaymentFilterVO;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountPayment.service.AccountPaymentService;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(RestPath.V1+"/payment")
@Tag(name = "Payment", description = "Cadastro de contas pagas")
@SecurityRequirement(name = "Authorization")
public class AccountPaymentController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/payment";

    @Autowired
    private AccountPaymentService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastro de novas contas pagas")
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
    public ResponseEntity<AccountPaymentInputDTO> save(@RequestBody @Valid AccountPaymentInputDTO inputDTO, UriComponentsBuilder uriComponentsBuilder) {
        AccountPayment accountPayment = AccountPaymentMapper.toModel(inputDTO);

        AccountPayment accountPaymentSaved = service.save(accountPayment);
        AccountPaymentInputDTO paymentInputDTO = AccountPaymentMapper.toInputDTO(accountPaymentSaved);

        return buildSuccessResponseCreated(paymentInputDTO, uriComponentsBuilder, ENDPOINT, accountPaymentSaved.getId());
    }

    @GetMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Faz a pesquisa com filtro e paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna contas pagas", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
    })
    public ResponseEntity<Page<AccountPaymentDTO>> findByFilter(@RequestParam(value = "search", required = false) String search,
                                                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                @RequestParam(value = "accountId", required = false) Long accountId){
        AccountPaymentFilterVO filterVO = new AccountPaymentFilterVO(limit, page, search);
        filterVO.setAccountId(accountId);

        Page<AccountPayment> pageAccountPayment = service.findPaymentByFilter(filterVO);
        Page<AccountPaymentDTO> pageAccountPaymentDTO = AccountPaymentMapper.toPageDTO(pageAccountPayment);

        return buildSuccessResponse(pageAccountPaymentDTO);
    }
}
