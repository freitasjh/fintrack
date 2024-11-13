package br.com.systec.fintrack.v1.controller;

import br.com.systec.fintrack.bank.filter.FilterBankVO;
import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bank.service.BankService;
import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.v1.converter.BankConverter;
import br.com.systec.fintrack.v1.dto.BankDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/banks")
@Tag(name = "Banks", description = "Cadastro de bancos")
@SecurityRequirement(name = "Authorization")
public class BankController extends AbstractController {

    @Autowired
    private BankService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os dados do cliente", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
    })
    public ResponseEntity<Page<BankDTO>> findByFilterAndPageable(@RequestParam(value = "search", required = false) String search,
                                                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(value = "limit", required = false, defaultValue = "30") int limit) {
        FilterBankVO filterVO = new FilterBankVO(limit, page, search);
        Page<Bank> listOfBank = service.findByPage(filterVO);

        return buildSuccessResponse(BankConverter.getInstance().toPageDTO(listOfBank));
    }
}
