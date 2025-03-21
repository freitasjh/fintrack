package br.com.systec.fintrack.creditcard.invoice.impl.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.jms.CreditCardInvoiceJmsVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreditCardInvoiceJms {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceJms.class);

    @Autowired
    private CreditCardInvoiceService service;

    @RabbitListener(queues = RabbitMQConstants.CREDIT_CARD_NEW_INVOICE)
    @Transactional(propagation = Propagation.REQUIRED)
    public void readyJms(@Payload Message message) {
        try {
            CreditCardInvoiceJmsVO jmsVO = (CreditCardInvoiceJmsVO) message.getPayload();

            TenantContext.add(jmsVO.getTenantId());
            CreditCard creditCard = new CreditCard();
            creditCard.setId(jmsVO.getCreditCardId());

            service.findByDateIfNotExistCreate(creditCard);
        } catch (Exception e) {
            log.error("Erro");
        } finally {
            TenantContext.clear();
        }
    }

    @RabbitListener(queues = RabbitMQConstants.CREDIT_CARD_INVOICE_PENDING)
    @Transactional(propagation = Propagation.REQUIRED)
    public void pendingJms(@Payload Message message) {
        try {
            CreditCardInvoiceJmsVO jmsVO = (CreditCardInvoiceJmsVO) message.getPayload();

            TenantContext.add(jmsVO.getTenantId());

            service.validateAndMarkInvoiceAsPending(jmsVO.getCreditCardId());

        } catch (Exception e) {
            log.error("Erro");
        } finally {
            TenantContext.clear();
        }
    }
}
