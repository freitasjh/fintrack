package br.com.systec.controle.financeiro.creditCard.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.repository.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CreditCard update(CreditCard creditCard) throws BaseException {
        try{
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void findByFilter() {
    }

    private CreditCard findById(Long id) throws BaseException {
        try {
          return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cartão de credito não foi encontrado"));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e){
            throw new BaseException(e.getMessage(), e);
        }
    }
}
