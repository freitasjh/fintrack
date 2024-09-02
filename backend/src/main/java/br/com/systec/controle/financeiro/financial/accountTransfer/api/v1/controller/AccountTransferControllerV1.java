package br.com.systec.controle.financeiro.financial.accountTransfer.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import br.com.systec.controle.financeiro.commons.exception.ValidationError;
import br.com.systec.controle.financeiro.financial.accountTransfer.api.v1.dto.AccountTransferInputDTO;
import br.com.systec.controle.financeiro.financial.accountTransfer.api.v1.dto.AccountTransferResponseDTO;
import br.com.systec.controle.financeiro.financial.accountTransfer.api.v1.mapper.AccountTransferMapper;
import br.com.systec.controle.financeiro.financial.accountTransfer.filter.AccountTransferFilterVO;
import br.com.systec.controle.financeiro.financial.accountTransfer.model.AccountTransfer;
import br.com.systec.controle.financeiro.financial.accountTransfer.service.AccountTransferService;
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
@RequestMapping(RestPath.V1+"/transfers")
@Tag(name = "Transfer", description = "Cadastro de transferencias")
@SecurityRequirement(name = "Authorization")
public class AccountTransferControllerV1 extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/transfers";
    @Autowired
    private AccountTransferService service;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "OK", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountTransferInputDTO.class))
        }),
        @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
        }),
        @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
        })
    })
    public ResponseEntity<AccountTransferInputDTO> save(@RequestBody @Valid AccountTransferInputDTO inputDTO, UriComponentsBuilder uriComponentsBuilder) {
        AccountTransfer accountTransfer = AccountTransferMapper.toEntity(inputDTO);

        AccountTransfer accountTransferSaved = service.save(accountTransfer);
        AccountTransferInputDTO accountTransferInputDTO = AccountTransferMapper.toInputDTO(accountTransferSaved);

        return buildSuccessResponseCreated(accountTransferInputDTO, uriComponentsBuilder, ENDPOINT, accountTransferSaved.getId());
    }

    @GetMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Faz a pesquisa com filtro e paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as transferencias", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
    })
    public ResponseEntity<Page<AccountTransferResponseDTO>> findByFilter(@RequestParam(value = "search", required = false) String search,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                         @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                         @RequestParam(value = "bankaccountfromid", required = false) Long bankAccountFromId,
                                                                         @RequestParam(value = "bankaccounttoid", required = false) Long bankAccountToId) {
        AccountTransferFilterVO filterVO = new AccountTransferFilterVO(limit, page, search);
        filterVO.setBankAccountFromId(bankAccountFromId);
        filterVO.setBankAccountToId(bankAccountToId);

        Page<AccountTransfer> pageAccountTransfer = service.findByFilter(filterVO);

        return buildSuccessResponse(AccountTransferMapper.toPageResponseDTO(pageAccountTransfer));

    }
}
