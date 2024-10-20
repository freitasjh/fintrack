package br.com.systec.fintrack.financial.received.impl.jms;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BankAccountNewJms {
    private static final Logger log = LoggerFactory.getLogger(BankAccountJmsVO.class);

    @Autowired
    private AccountReceivableService service;

    @RabbitListener(queues = RabbitMQConstants.BANK_ACCOUNT_NEW)
    public void saveNewAccountReceivableForNewBankAccount(BankAccountJmsVO bankAccountJmsVO) {
        try {
            log.info("@@@@@ salvando o valor inicial da conta");
            TenantContext.add(bankAccountJmsVO.getTenantId());

            AccountReceivableVO receive = new AccountReceivableVO();
            receive.setDateProcessed(new Date());
            receive.setDateRegister(new Date());
            receive.setProcessed(true);
            receive.setBankAccount(new BankAccount(bankAccountJmsVO.getBankAccountId()));
            receive.setDescription(I18nTranslate.toLocale("account.opening.balance"));
            receive.setAmount(bankAccountJmsVO.getInitialValue());
            receive.setTenantId(bankAccountJmsVO.getTenantId());
            receive.setCategoryTransactionType(CategoryTransactionType.INITIAL_AMOUNT);

            log.info("Salvando");

            AccountReceivableVO accountReceivableVO = service.save(receive);
            log.info("@@@@ Salvo {}", accountReceivableVO.getId());
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o valor inicial do banco no recebido", e);
        }
    }
}
