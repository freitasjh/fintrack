package br.com.systec.fintrack.bankaccount.impl.jms;


import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountBalanceUpdateJms {

    public static final Logger log = LoggerFactory.getLogger(BankAccountNewAccountJms.class);

    @Autowired
    private BankAccountService service;

    @RabbitListener(queues = RabbitMQConstants  .BANK_ACCOUNT_BALANCE_UPDATE)
    public void updateBalanceBankAccount(BankAccountJms bankAccountJms) {
        try {
            service.updateBankAccountBalance(bankAccountJms.getAmountBalance(), bankAccountJms.getBankAccountId(), bankAccountJms.getTransactionType());
        }catch (Exception e){
            log.error("Erro", e);
        }
    }
}
