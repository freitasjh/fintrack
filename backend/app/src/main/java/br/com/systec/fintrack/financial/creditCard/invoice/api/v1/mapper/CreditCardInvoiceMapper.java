package br.com.systec.fintrack.financial.creditCard.invoice.api.v1.mapper;

import br.com.systec.fintrack.financial.creditCard.invoice.api.v1.dto.CreditCardInvoiceDTO;
import br.com.systec.fintrack.financial.creditCard.invoice.model.CreditCardInvoice;

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
}
