package br.com.systec.controle.financeiro.administrator.bankAccount.jms;


import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
public class BankAccountBalanceUpdateJms {

    public static final Logger log = LoggerFactory.getLogger(BankAccountNewAccountJms.class);

    @Autowired
    private BankAccountService service;

    @RabbitListener(queues = RabbitMQConfig.BANK_ACCOUNT_BALANCE_UPDATE)
    public void updateBalanceBankAccount(BankAccountJms bankAccountJms) {
        try {
            service.updateBankAccountBalance(bankAccountJms.getAmountBalance(), bankAccountJms.getBankAccountId(), bankAccountJms.getTransactionType());
        }catch (Exception e){
            log.error("Erro", e);
        }
    }
}
