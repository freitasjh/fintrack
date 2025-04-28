package br.com.systec.fintrack.financial.payment.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PaymentRegisterException extends BaseException {

    public PaymentRegisterException() {
        super("Pagamento já foi registrado", HttpStatus.BAD_REQUEST);
    }
}
