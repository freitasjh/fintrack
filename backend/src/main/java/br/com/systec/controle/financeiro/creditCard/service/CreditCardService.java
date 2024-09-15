package br.com.systec.controle.financeiro.creditCard.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.creditCard.filter.CreditCardFilterVO;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.repository.CreditCardRepository;
import br.com.systec.controle.financeiro.creditCard.repository.CreditCardRepositoryJPA;
import br.com.systec.controle.financeiro.financial.creditCard.commons.CreditCardTransactionType;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardTransaction;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CreditCardService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardService.class);
    @Autowired
    private CreditCardRepository repository;
    @Autowired
    private CreditCardRepositoryJPA repositoryJPA;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCard save(CreditCard creditCard) throws  BaseException {
        try{
            if (creditCard.getTenantId() == null) {
                creditCard.setTenantId(TenantContext.getTenant());
            }

            return repository.save(creditCard);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o cartao de credito", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCard update(CreditCard creditCard, Long id) throws BaseException {
        try{
            CreditCard creditCardBeforeUpdate = findCreditCardById(id);
            creditCard.setDateCreated(creditCardBeforeUpdate.getDateCreated());

            return repository.update(creditCard);
        } catch (BaseException e){
            throw e;
        } catch (Exception e) {
           log.error("Ocorreu um erro ao tentar salvar os dados do Cartão de credito", e);
           throw new BaseException(e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCard> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<CreditCard> findByFilter(CreditCardFilterVO filterVO) throws BaseException {
        try {
            Page<CreditCard> pageOfCreditCard = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

            return pageOfCreditCard;
        }catch (Exception e) {
            log.error("Erro ao tentar pesquisar os Cartões de Credito", e);
            throw new BaseException("Erro ao tentar pesquiar os Cartoes de credito", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public CreditCard findById(Long id) throws BaseException {
        return findCreditCardById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAvailableLimitCreditCard(double amount, Long id, CreditCardTransactionType transactionType) throws BaseException {
        try {
            CreditCard creditCard = findCreditCardById(id);

            double newLimitAvailable = creditCard.getAvailableLimit();

            if (transactionType == CreditCardTransactionType.EXPENSE) {
                newLimitAvailable = newLimitAvailable - amount;
            } else {
                newLimitAvailable = newLimitAvailable + amount;
            }

            creditCard.setAvailableLimit(newLimitAvailable);

            repository.update(creditCard);
        } catch (BaseException e) {
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
        } catch (Exception e){
            throw new BaseException(e.getMessage(), e);
        }
    }
}
