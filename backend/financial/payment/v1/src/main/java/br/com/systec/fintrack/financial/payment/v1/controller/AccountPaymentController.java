package br.com.systec.fintrack.financial.payment.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
import br.com.systec.fintrack.financial.payment.filter.AccountPaymentFilterType;
import br.com.systec.fintrack.financial.payment.filter.AccountPaymentPageParam;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentDTO;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentInputDTO;
import br.com.systec.fintrack.financial.payment.v1.mapper.AccountPaymentMapper;
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

import java.util.Date;

@RestController
@RequestMapping(RestPath.V1+"/payments")
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
                                                                @RequestParam(value = "accountId", required = false) Long accountId,
                                                                @RequestParam(value = "paymentFilterType", required = false, defaultValue = "2") String paymentFilterType){
        AccountPaymentPageParam pageParam = new AccountPaymentPageParam(limit, page, search);
        pageParam.getFilterVO().setKeywordSearch(search);
        pageParam.getFilterVO().setBankAccountId(accountId);
        pageParam.getFilterVO().setFilterType(AccountPaymentFilterType.valueOfCode(paymentFilterType));

        Page<AccountPayment> pageAccountPayment = service.findPaymentByFilter(pageParam);
        Page<AccountPaymentDTO> pageAccountPaymentDTO = AccountPaymentMapper.toPageDTO(pageAccountPayment);

        return buildSuccessResponse(pageAccountPaymentDTO);
    }

    @PutMapping(value = "/register-payment/{id}/{dateregister}")
    @Operation(description = "Registra o pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ok"),
    })
    public ResponseEntity<Void> registerPayment(@PathVariable("id") Long id, @PathVariable("dateregister") Date dateRegister) {
        service.registerPayment(id, dateRegister);

        return buildSuccessResponseNoContent();
    }
}
