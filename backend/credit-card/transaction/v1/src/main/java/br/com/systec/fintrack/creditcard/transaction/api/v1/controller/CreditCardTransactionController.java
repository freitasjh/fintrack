package br.com.systec.fintrack.creditcard.transaction.api.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.mapper.CreditCardTransactionMapper;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionFilterVO;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardTransactionService;
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
@RequestMapping(RestPath.V1+"/credit-cards/transactions")
@Tag(name = "Credit Card Transaction", description = "Transações de cartão de credito")
@SecurityRequirement(name = "Authorization")
public class CreditCardTransactionController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/credit-cards/transactions";
    @Autowired
    private CreditCardTransactionService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastra uma nova transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardTransactionInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CreditCardTransactionInputDTO> save(@RequestBody @Valid CreditCardTransactionInputDTO inputDTO, UriComponentsBuilder uri) {
        CreditCardTransaction transaction = CreditCardTransactionMapper.toEntity(inputDTO);

        CreditCardTransaction transactionSaved = service.save(transaction);
        CreditCardTransactionInputDTO transactionSaveConverted = CreditCardTransactionMapper.toInputDTO(transactionSaved);

        return buildSuccessResponseCreated(transactionSaveConverted, uri, ENDPOINT, transactionSaved.getId());
    }

    @GetMapping("/filter")
    @Operation(description = "Faz a pesquisa com filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedList.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<PaginatedList<CreditCardTransactionDTO>> findByFilter(@RequestParam(value = "search", required = false) String search,
                                                                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                                @RequestParam(value = "limit", required = false, defaultValue = "30") int limit) {

        CreditCardTransactionPageParam pageParam = new CreditCardTransactionPageParam(limit, page);
        pageParam.setFilterVO(new CreditCardTransactionFilterVO());
        pageParam.getFilterVO().setKeyword(search);

        PaginatedList<CreditCardTransaction> pageOfCreditCardTransaction = service.findByFilter(pageParam);

        return buildSuccessResponse(CreditCardTransactionMapper.toPageDTO(pageOfCreditCardTransaction));
    }
}
