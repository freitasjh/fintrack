package br.com.systec.fintrack.financial.transaction.impl.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.transaction.impl.repository.TransactionRepository;
import br.com.systec.fintrack.financial.transaction.service.TransactionService;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionRepository repository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CategoryExpenseVO> findExpenseByCategory() {
        try {
            List<CategoryExpenseVO> listOfCategoryExpense = repository.findTotalExpenseCategory();
            return listOfCategoryExpense;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar gerar o total gasto por categoria", e);
            throw new BaseException("Ocorreu um erro ao gerar o total gasto por categoria", e);
        }
    }
}
