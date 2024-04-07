package br.com.systec.controle.financeiro.receive.service;

import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.receive.model.Receive;
import br.com.systec.controle.financeiro.receive.repository.ReceiveRepository;
import br.com.systec.controle.financeiro.receive.repository.ReceiveRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceiveService {

    @Autowired
    private ReceiveRepository repository;
    @Autowired
    private ReceiveRepositoryJPA repositoryJPA;

    @Transactional(propagation = Propagation.REQUIRED)
    public Receive save(Receive receive) {
        return repository.save(receive);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Receive update(Receive receive){
        return repository.update(receive);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Receive receive = findById(id);

        repository.delete(receive);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Receive findReceiveById(Long id){
        return findById(id);
    }

    private Receive findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(I18nTranslate.toLocale("receive.not.found")));
    }
}
