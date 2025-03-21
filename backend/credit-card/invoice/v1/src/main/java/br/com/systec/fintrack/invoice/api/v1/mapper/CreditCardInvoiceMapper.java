package br.com.systec.fintrack.invoice.api.v1.mapper;

import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoiceDTO;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoiceInputDTO;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoiceInstalmentResponseDTO;
import br.com.systec.fintrack.invoice.api.v1.dto.CreditCardInvoicePaymentDTO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.vo.CreditCardInstallmentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoicePaymentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO;

import java.util.List;

public class CreditCardInvoiceMapper {


    public static CreditCardInvoiceDTO toDTO(CreditCardInvoice creditCardInvoice) {
        CreditCardInvoiceDTO invoiceDTO = new CreditCardInvoiceDTO();
        invoiceDTO.setId(creditCardInvoice.getId());
        invoiceDTO.setCreditCardName(creditCardInvoice.getCreditCard().getName());
        invoiceDTO.setStatusType(creditCardInvoice.getStatusType());
        invoiceDTO.setDueDate(creditCardInvoice.getDueDate());
        invoiceDTO.setDateClose(creditCardInvoice.getDateClose());

        return invoiceDTO;
    }

    public static CreditCardInvoicePaymentVO toInvoicePaymentVO(CreditCardInvoicePaymentDTO paymentDTO) {
        CreditCardInvoicePaymentVO paymentVO = new CreditCardInvoicePaymentVO();
        paymentVO.setCreditCardInvoiceId(paymentDTO.getCreditCardInvoiceId());
        paymentVO.setAmount(paymentDTO.getAmount());
        paymentVO.setBankAccountId(paymentDTO.getBankAccountId());
        paymentVO.setDatePay(paymentDTO.getDatePay());

        return paymentVO;
    }

    public static CreditCardInvoiceInputDTO toInvoiceDTO(CreditCardInvoiceResultVO invoiceVO) {
        CreditCardInvoiceInputDTO creditCardInvoiceInputDTO = new CreditCardInvoiceInputDTO();
        creditCardInvoiceInputDTO.setId(invoiceVO.id());
        creditCardInvoiceInputDTO.setDueDate(invoiceVO.dueDate());
        creditCardInvoiceInputDTO.setInvoiceStatusType(invoiceVO.statusType());
        creditCardInvoiceInputDTO.setCreditCardDescription(invoiceVO.creditCardDescription());

        return creditCardInvoiceInputDTO;
    }

    public static CreditCardInvoiceInstalmentResponseDTO toInstallmentDTO(CreditCardInstallmentVO creditCardInstallment) {
        CreditCardInvoiceInstalmentResponseDTO instalmentResponse = new CreditCardInvoiceInstalmentResponseDTO();
        instalmentResponse.setDescription(creditCardInstallment.getDescription());
        instalmentResponse.setInstallment(creditCardInstallment.getInstallment());
        instalmentResponse.setAmount(creditCardInstallment.getAmount());
        instalmentResponse.setDateCreate(creditCardInstallment.getDateCreate());

        return instalmentResponse;
    }

    public static List<CreditCardInvoiceInstalmentResponseDTO> toListInstallResponseDTO(List<CreditCardInstallmentVO> listOfInstallment) {
        return listOfInstallment.stream().map(CreditCardInvoiceMapper::toInstallmentDTO).toList();
    }
}
