package br.com.systec.fintrack.creditcard.commons;

import br.com.systec.fintrack.creditcard.model.CreditCard;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateCreditCardUtils {

    public static LocalDate generateDateCloseWithDateTransaction(CreditCard creditCard, LocalDate dateTransaction) {
        int closingDay = Integer.parseInt(creditCard.getClosingDate());

        LocalDate dateClose = dateTransaction.withDayOfMonth(closingDay);

        // Se a transação for após o fechamento, a fatura fecha no mês seguinte
        if (dateTransaction.isAfter(dateClose)) {
            dateClose = dateClose.plusMonths(1);
        }

        return dateClose;
    }

    public static LocalDate generateDueDateWithDateTransaction(CreditCard creditCard, int installment, LocalDate dateTransaction) {
        LocalDate dateClose = generateDateCloseWithDateTransaction(creditCard, dateTransaction);
        LocalDate dueDate = dateClose.withDayOfMonth(Integer.parseInt(creditCard.getDueDay()))
                .plusMonths(installment - 1);

        if (dateTransaction.isBefore(dateClose) || dateTransaction.isEqual(dateClose)) {
            if (Integer.parseInt(creditCard.getDueDay()) < Integer.parseInt(creditCard.getClosingDate())) {
                return dueDate.plusMonths(1);
            }
        }

        return dueDate;
    }
}
