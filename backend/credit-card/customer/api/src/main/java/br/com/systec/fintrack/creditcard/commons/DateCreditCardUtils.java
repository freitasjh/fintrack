package br.com.systec.fintrack.creditcard.commons;

import br.com.systec.fintrack.creditcard.model.CreditCard;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateCreditCardUtils {

    public static LocalDate generateDueDate(CreditCard creditCard, int installment) {
        LocalDate dateClose = LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getClosingDate()));
        LocalDate dateNow = LocalDate.now();

        if (installment > 1 || dateNow.isAfter(dateClose)) {
            if (installment == 1) {
                ++installment;
            }

            return LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getDueDay())).plusMonths(installment);
        }

        return LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getDueDay())).plusMonths(installment);
    }
}
