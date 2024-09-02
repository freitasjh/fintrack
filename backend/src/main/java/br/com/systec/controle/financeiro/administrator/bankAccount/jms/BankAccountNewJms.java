package br.com.systec.controle.financeiro.administrator.bankAccount.jms;

import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import br.com.systec.controle.financeiro.financial.transaction.enums.CategoryTransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BankAccountNewJms {
    private static final Logger log = LoggerFactory.getLogger(BankAccountNewAccountJms.class);
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private AccountReceivableService receivableService;

    @RabbitListener(queues = RabbitMQConfig.BANK_ACCOUNT_NEW)
    public void saveNewAccountReceivableForNewBankAccount(BankAccountJmsVO bankAccountJmsVO) {
        try {
            TenantContext.add(bankAccountJmsVO.getTenantId());

            AccountReceivable receive = new AccountReceivable();
            receive.setDateProcessed(new Date());
            receive.setDateRegister(new Date());
            receive.setProcessed(true);
            receive.setBankAccount(new BankAccount(bankAccountJmsVO.getBankAccountId()));
            receive.setDescription(I18nTranslate.toLocale("account.opening.balance"));
            receive.setAmount(bankAccountJmsVO.getInitialValue());
            receive.setTenantId(bankAccountJmsVO.getTenantId());
            receive.setCategoryTransactionType(CategoryTransactionType.INITIAL_AMOUNT);

            receivableService.save(receive);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o valor inicial do banco no recebido", e);
        }
    }

}
