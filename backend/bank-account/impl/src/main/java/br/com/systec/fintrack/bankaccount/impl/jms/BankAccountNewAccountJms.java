package br.com.systec.fintrack.bankaccount.impl.jms;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.bankaccount.service.BankAccountService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class BankAccountNewAccountJms {
    private static final Logger log = LoggerFactory.getLogger(BankAccountNewAccountJms.class);

    @Autowired
    private BankAccountService service;

    @RabbitListener(queues = RabbitMQConstants.NEW_ACCOUNT_BANK_ACCOUNT_QUEUE)
    public void createBankAccountForTenant(@Payload Message message) {
        try {
            String tenantId = String.valueOf(message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("json/bankAccount-insert.json")) {
                List<BankAccount> listOfBankAccount = objectMapper.readValue(inputStream,new TypeReference<List<BankAccount>>() {});

                for(BankAccount bankAccount : listOfBankAccount) {
                    bankAccount.setTenantId(Long.parseLong(tenantId));
                    service.save(bankAccount);
                    log.info("BankAccount {}", bankAccount.toString());
                }
            }
        } catch (Exception e) {
            log.error("Erro ao tentar salvar as contas padrão", e);
        }
    }
}
