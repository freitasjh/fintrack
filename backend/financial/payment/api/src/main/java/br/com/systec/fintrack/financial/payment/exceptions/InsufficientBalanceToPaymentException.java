package br.com.systec.fintrack.financial.payment.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceToPaymentException extends BaseException {

    public InsufficientBalanceToPaymentException() {
        super("Saldo insuficiente para realizar o pagamento", HttpStatus.BAD_REQUEST);
    }
}
