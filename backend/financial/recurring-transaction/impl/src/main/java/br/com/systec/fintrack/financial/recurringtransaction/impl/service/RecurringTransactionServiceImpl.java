package br.com.systec.fintrack.financial.recurringtransaction.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.financial.recurringtransaction.impl.mapper.RecurringTransactionMapper;
import br.com.systec.fintrack.financial.recurringtransaction.impl.repository.RecurringTransactionRepository;
import br.com.systec.fintrack.financial.recurringtransaction.jms.RecurringTransactionJobVO;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.service.RecurringTransactionService;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;
import br.com.systec.fintrack.quartz.service.JobService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    @Autowired
    private RecurringTransactionRepository repository;
    @Autowired
    private JobService jobService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RecurringTransactionVO create(RecurringTransactionVO recurringTransactionVO) throws BaseException {
        recurringTransactionVO.setTenantId(TenantContext.getTenant());

        RecurringTransaction recurringTransactionToSave = RecurringTransactionMapper.toEntity(recurringTransactionVO);
        RecurringTransaction recurringTransactionAfterSave = repository.save(recurringTransactionToSave);

        recurringTransactionVO = RecurringTransactionMapper.toVO(recurringTransactionAfterSave);

        createJobRecurringTransaction(recurringTransactionVO);

        return recurringTransactionVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RecurringTransactionVO update(RecurringTransactionVO recurringTransaction) throws BaseException {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long transactionId) throws BaseException {
         repository.deleteFromTransactionIdAndTenant(transactionId);
    }

    @Override
    public RecurringTransactionVO findByIdAndTenant(Long id) throws BaseException {
        RecurringTransaction recurringTransaction = repository.findByIdAndTenant(id).orElseThrow(() ->
                new ObjectNotFoundException("Não Encontrado"));

        RecurringTransactionVO recurringTransactionVO = RecurringTransactionMapper.toVO(recurringTransaction);

        return recurringTransactionVO;
    }

    private void createJobRecurringTransaction(RecurringTransactionVO recurringTransaction) {
        RecurringTransactionJobVO recurringTransactionJobVO = new RecurringTransactionJobVO();
        recurringTransactionJobVO.setTenantId(TenantContext.getTenant());
        recurringTransactionJobVO.setTransactionId(recurringTransaction.getTransactionId());
        recurringTransactionJobVO.setRecurringTransactionId(recurringTransaction.getId());
        recurringTransactionJobVO.setTransactionTypeCode(recurringTransaction.getTransactionType().getCode());
        recurringTransactionJobVO.setCronJob(recurringTransaction.getFrequencyType().getCronJob());

        rabbitTemplate.convertAndSend(RabbitMQConstants.RECURRING_FINANCIAL_JOB, recurringTransactionJobVO);
    }
}
