package br.com.systec.fintrack.creditcard.invoice.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.invoice.impl.repository.CreditCardInvoiceRepository;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.model.InvoiceStatusType;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditCardInvoiceServiceImpl implements CreditCardInvoiceService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceServiceImpl.class);

    @Autowired
    private CreditCardInvoiceRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException {
        CreditCardInvoice creditCardInvoice = repository.findByDateClose(LocalDate.now()).orElse(null);

        if (creditCardInvoice == null) {
            return create(creditCard);
        }

        return creditCardInvoice;
    }

    private CreditCardInvoice create(CreditCard creditCard) throws BaseException {
        try {
            CreditCardInvoice creditCardInvoice = new CreditCardInvoice();
            creditCardInvoice.setCreditCard(creditCard);

            creditCardInvoice.setDateClose(LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getClosingDate())));
            if (Integer.parseInt(creditCard.getClosingDate()) < Integer.parseInt(creditCard.getDueDay())){
                creditCardInvoice.setDateClose(LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getClosingDate())).plusMonths(1));
            }

            creditCardInvoice.setDueDate(LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getDueDay())).plusMonths(1));
            creditCardInvoice.setTenantId(TenantContext.getTenant());
            creditCardInvoice.setStatusType(InvoiceStatusType.OPEN);

            return repository.save(creditCardInvoice);
        } catch (Exception e) {
            log.error("Erro ao tentar salvar a fatura", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInvoiceVO> findByFilter(CreditCardInvoiceFilterVO filterVO) throws BaseException {
        return repository.findInvoiceByFilter(filterVO);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardFutureInvoiceVO> findFutureInvoiceByFilter() throws BaseException {
        return repository.findAllFutureInvoice(LocalDate.now());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findAmountInvoiceOpen() throws BaseException {
        return repository.findAmountInvoiceOpen();
    }
}
