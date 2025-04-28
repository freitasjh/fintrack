package br.com.systec.fintrack.publicapi.v1.controller;

import br.com.systec.fintrack.bankaccount.filter.BankAccountFilterVO;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.bankaccount.service.BankAccountService;
import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.exception.ValidationError;
import br.com.systec.fintrack.publicapi.v1.converter.BankAccountConverter;
import br.com.systec.fintrack.publicapi.v1.dto.BankAccountDTO;
import br.com.systec.fintrack.publicapi.v1.dto.BankAccountInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping(RestPath.V1 + "/bank-accounts")
@Tag(name = "Bank accounts", description = "Cadastro de contas bancarias")
@SecurityRequirement(name = "Authorization")
public class BankAccountController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(BankAccountController.class);
    private static final String ENDPOINT = RestPath.V1 + "/bankAccounts";

    @Autowired
    private BankAccountService service;

    @Autowired
    private BankAccountConverter converter;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Cadastra novas contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BankAccountInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<BankAccountInputDTO> save(@RequestBody @Valid BankAccountInputDTO inputDTO, UriComponentsBuilder builder) {
        try {
            BankAccount bankAccount = converter.convertToModel(inputDTO);

            BankAccount bankAccountSaved = service.save(bankAccount);

            return buildSuccessResponseCreated(converter.convertTOInputDTO(bankAccountSaved), builder, ENDPOINT, bankAccountSaved.getId());
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BaseException("Ocorreu um erro ao tentar salvar a conta", e);
        }
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualiza as contas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BankAccountInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<BankAccountInputDTO> update(@RequestBody @Valid BankAccountInputDTO inputDTO) {
        try{
            BankAccount bankAccount = converter.convertToModel(inputDTO);

            BankAccount bankAccountSaved = service.update(bankAccount);

            return buildSuccessResponse(converter.convertTOInputDTO(bankAccountSaved));
        }catch (BaseException e){
            throw e;
        }catch (Exception e){
            throw new BaseException("Ocorreu um erro ao tentar atualizar a conta", e);
        }
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Retorna os dados da conta filtrada pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BankAccountInputDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Conta não encontrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<BankAccountInputDTO> findById(@PathVariable("id") Long id) {
        try{
            BankAccount bankAccount = service.findById(id);
            return buildSuccessResponse(converter.convertTOInputDTO(bankAccount));
        }catch (BaseException e){
            throw e;
        }catch (Exception e){
            throw new BaseException("Ocorreu um erro ao tentar pesquisar a conta");
        }
    }

    @GetMapping("/filter")
    @Operation(description = "Faz a pesquisa com filtro e paginada das contas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as contas cadastradas", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
    })
    public ResponseEntity<Page<BankAccountDTO>> findByFilter(@RequestParam(value = "search", required = false) String search,
                                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                             @RequestParam(value = "limit", required = false, defaultValue = "30") int limit) {

        BankAccountFilterVO filterBankVO = new BankAccountFilterVO(limit, page, search);

        Page<BankAccount> pageBankAccount = service.findByFilter(filterBankVO);

        Page<BankAccountDTO> pageBankAccountDTO = converter.convertePage(pageBankAccount);

        return buildSuccessResponse(pageBankAccountDTO);
    }
}
