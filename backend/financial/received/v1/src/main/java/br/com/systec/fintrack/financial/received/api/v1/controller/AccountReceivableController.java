package br.com.systec.fintrack.financial.received.api.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
import br.com.systec.fintrack.financial.received.api.v1.converter.AccountReceivableConverter;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(RestPath.V1 + "/receivable")
@Tag(name = "Account Receivable", description = "Cadastro de Recebidos")
@SecurityRequirement(name = "Authorization")
public class AccountReceivableController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/receivable";

    @Autowired
    private AccountReceivableService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AccountReceivableDTO>> findAll() {
        List<AccountReceivableVO> listAll = service.findAll();

        List<AccountReceivableDTO> listAccountReceivableDTO = AccountReceivableConverter.toListDTO(listAll);

        return buildSuccessResponse(listAccountReceivableDTO);
    }

    @GetMapping(value = "/filter", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Pesquisa as contras com filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as contas cadastradas", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
    })
    public ResponseEntity<Page<AccountReceivableDTO>> filterByFilter(@RequestParam(value = "search", required = false) String search,
                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                     @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                     @RequestParam(value = "accountId", required = false) Long accountId) {
        AccountReceivableFilterVO filterVO = new AccountReceivableFilterVO(limit, page, search);
        filterVO.setAccountId(accountId);

        Page<AccountReceivableVO> pageOfAccountReceivable = service.findByFilter(filterVO);
        Page<AccountReceivableDTO> pageOfAccountReceivableDTO = AccountReceivableConverter.toPageDTO(pageOfAccountReceivable);

        return buildSuccessResponse(pageOfAccountReceivableDTO);
    }

    @GetMapping(value = "/{accountReceivableId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Retorna os dados da conta a receber filtrada pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountReceivableInputDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Conta não encontrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<AccountReceivableInputDTO> findById(@PathVariable("accountReceivableId") Long accountReceivableId) {
        AccountReceivableVO accountReceivable = service.findById(accountReceivableId);

        AccountReceivableInputDTO inputDTO = AccountReceivableConverter.toInputDTO(accountReceivable);

        return buildSuccessResponse(inputDTO);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountReceivableInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<AccountReceivableInputDTO> save(@RequestBody @Valid AccountReceivableInputDTO inputDTO,
                                                          UriComponentsBuilder builder) {
        AccountReceivableVO accountReceivable = AccountReceivableConverter.toVO(inputDTO);

        AccountReceivableVO accountReceivableAfterSave = service.save(accountReceivable);

        AccountReceivableInputDTO accountReceivableInputDTO = AccountReceivableConverter.toInputDTO(accountReceivableAfterSave);

        return buildSuccessResponseCreated(accountReceivableInputDTO, builder, ENDPOINT, accountReceivableAfterSave.getId());
    }
}
