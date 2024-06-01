package br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import static br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.converter.AccountReceivableConverter.*;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableDTO;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(RestPath.V1+"/accountReceivable")
@Tag(name = "Contas a receber")
@SecurityRequirement(name = "Authorization")
public class AccountReceivableController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/receivers";
    @Autowired
    private AccountReceivableService service;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Salvar novo contas a receber")
    public ResponseEntity<AccountReceivableInputDTO> save(@RequestBody @Valid AccountReceivableInputDTO inputDTO, UriComponentsBuilder builder){
        AccountReceivable receive = convertToModel(inputDTO);
        AccountReceivable receiveSaved = service.save(receive);

        return buildSuccessResponseCreated(convertTOInputDTO(receiveSaved), builder, ENDPOINT, receiveSaved.getId());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Atualizar contas a receber")
    public ResponseEntity<AccountReceivableInputDTO> update(@RequestBody @Valid AccountReceivableInputDTO inputDTO){
        AccountReceivable receive = convertToModel(inputDTO);
        AccountReceivable receiveUpdated = service.update(receive);

        return buildSuccessResponse(convertTOInputDTO(receiveUpdated));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar contas a receber")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Retorna conta a receber")
    public ResponseEntity<AccountReceivableInputDTO> findById(@PathVariable("id") Long id) {
        AccountReceivable accountReceivable = service.findAccountReceivableById(id);

        AccountReceivableInputDTO inputDTO = convertTOInputDTO(accountReceivable);

        return buildSuccessResponse(inputDTO);
    }

    @GetMapping("/filter")
    @Operation(description = "Pesquisa de contas a receber com filtro")
    public ResponseEntity<Page<AccountReceivableDTO>> findByFilter(@RequestParam(value = "search", required = false) String search,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
                                                                   @RequestParam(value = "accountId", required = false) Long accountId) {

        AccountReceivableFilterVO filterVO = new AccountReceivableFilterVO(limit, page, search);
        filterVO.setAccountId(accountId);

        Page<AccountReceivable> pageOfAccountReceivable = service.findByFilter(filterVO);
        Page<AccountReceivableDTO> pageOfAccountReceivableDTO = convertToPageDTO(pageOfAccountReceivable);

        return buildSuccessResponse(pageOfAccountReceivableDTO);
    }
}
