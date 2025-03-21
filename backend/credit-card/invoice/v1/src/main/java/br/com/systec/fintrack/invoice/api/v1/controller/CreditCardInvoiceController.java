package br.com.systec.fintrack.invoice.api.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoiceInputDTO;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoiceInstalmentResponseDTO;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoicePaymentDTO;
import br.com.systec.fintrack.invoice.api.v1.mapper.CreditCardInvoiceMapper;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInstallmentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoicePaymentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterVO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestPath.V1+"/credit-card/invoices")
@Tag(name = "Credit Card Invoices", description = "Faturas dos Cartão de Credito")
@SecurityRequirement(name = "Authorization")
public class CreditCardInvoiceController extends AbstractController {
    @Autowired
    private CreditCardInvoiceService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Pesquisa as Faturas aberta e paga por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardFutureInvoiceVO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<List<CreditCardInvoiceResultVO>> findAllInvoice(@RequestParam(value = "creditCardId", required = false) Long creditCardId) {
        CreditCardInvoiceFilterVO filterVO = new CreditCardInvoiceFilterVO();
        filterVO.setCreditCardId(creditCardId);

        return buildSuccessResponse(service.findByFilter(filterVO));
    }

    @GetMapping(value = "/future", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Faz as pequisas Futuras, que ainda não tem Fatura criada, apenas as parcelas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardFutureInvoiceVO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<List<CreditCardFutureInvoiceVO>> findFutureInvoiceByFilter() {
        return buildSuccessResponse(service.findFutureInvoiceByFilter());
    }

    @PutMapping("/register-payment")
    @Operation(description = "Registra o pagamento da fatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<String> registerPaymentInvoice(@RequestBody @Valid CreditCardInvoicePaymentDTO creditCardInvoicePayment) {
        CreditCardInvoicePaymentVO paymentVO = CreditCardInvoiceMapper.toInvoicePaymentVO(creditCardInvoicePayment);

        service.paymentInvoice(paymentVO);

        return buildSuccessResponse("Registrado com sucesso");
    }

    @GetMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Pesquisa as parcelas de uma fatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditCardInstallmentVO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<List<CreditCardInvoiceInstalmentResponseDTO>> findInstallmentByInvoiceId(@PathVariable(value = "id") Long id) {
        List<CreditCardInstallmentVO> listOfInstallment = service.findInstallmentByCreditCardInvoiceId(id);

        return buildSuccessResponse(CreditCardInvoiceMapper.toListInstallResponseDTO(listOfInstallment));
    }
}
