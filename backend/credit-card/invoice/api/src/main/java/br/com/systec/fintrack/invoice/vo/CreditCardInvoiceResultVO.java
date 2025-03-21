package br.com.systec.fintrack.invoice.vo;

import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public record CreditCardInvoiceResultVO(Long id, LocalDate dueDate,
                                        InvoiceStatusType statusType,
                                        String creditCardDescription,
                                        double amount
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 4575058838022315188L;
}
