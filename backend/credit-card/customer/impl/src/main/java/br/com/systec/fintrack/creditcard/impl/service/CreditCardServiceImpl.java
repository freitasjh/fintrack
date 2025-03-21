package br.com.systec.fintrack.creditcard.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.filter.CreditCardFilterVO;
import br.com.systec.fintrack.creditcard.impl.repository.CreditCardRepository;
import br.com.systec.fintrack.creditcard.impl.repository.CreditCardRepositoryJPA;
import br.com.systec.fintrack.creditcard.jms.CreditCardJobJmsVO;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.service.CreditCardService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    private CreditCardRepository repository;
    @Autowired
    private CreditCardRepositoryJPA repositoryJPA;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCard save(CreditCard creditCard) throws BaseException {
        try {
            if (creditCard.getTenantId() == null) {
                creditCard.setTenantId(TenantContext.getTenant());
            }

            CreditCard creditCardAfterSave = repository.save(creditCard);

            createQueue(creditCardAfterSave);

            return creditCardAfterSave;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o cartao de credito", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCard update(CreditCard creditCard, Long id) throws BaseException {
        try {
            CreditCard creditCardBeforeUpdate = findCreditCardById(id);
            creditCard.setDateCreated(creditCardBeforeUpdate.getDateCreated());

            return repository.update(creditCard);
        } catch (BaseException e) {
            log.error("Erro na atualização do cartão de crédito", e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar os dados do Cartão de crédito", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCard> findAll() throws BaseException {
        return StreamSupport.stream(repository.findAllByTenantId().spliterator(), false).toList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CreditCard> findByFilter(CreditCardFilterVO filterVO) throws BaseException {
        try {
            return repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());
        } catch (Exception e) {
            log.error("Erro ao tentar pesquisar os Cartões de Credito", e);
            throw new BaseException("Erro ao tentar pesquiar os Cartoes de credito", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CreditCard findById(Long id) throws BaseException {
        return findCreditCardById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAvailableLimitCreditCard(double amount, Long id, CreditCardTransactionType transactionType) throws
            BaseException {
        try {
            CreditCard creditCard = findCreditCardById(id);

            double newLimitAvailable = creditCard.getAvailableLimit();

            if (transactionType == CreditCardTransactionType.EXPENSE) {
                newLimitAvailable = newLimitAvailable - amount;
            } else {
                newLimitAvailable = newLimitAvailable + amount;
            }

            repository.updateCreditCardAvailableLimit(newLimitAvailable, creditCard.getId());
        } catch (BaseException e) {
            log.error("Ocorreu um erro ao tentar atualizar o limite disponivel do cartão", e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualizar o limite disponivel do cartão", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    private CreditCard findCreditCardById(Long id) throws BaseException {
        try {
            return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cartão de credito não foi encontrado"));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), e);
        }
    }

    private void createQueue(CreditCard creditCard) {
        CreditCardJobJmsVO creditCardJobJms = new CreditCardJobJmsVO();
        creditCardJobJms.setCreditCardId(creditCard.getId());
        creditCardJobJms.setTenantId(creditCard.getTenantId());
        creditCardJobJms.setCloseDay(creditCard.getClosingDate());
        creditCardJobJms.setDueDay(creditCard.getDueDay());
        creditCardJobJms.setCreditCardJobType("INSERT");

        rabbitTemplate.convertAndSend(RabbitMQConstants.CREDIT_CARD_JOB, creditCardJobJms);
    }
}
