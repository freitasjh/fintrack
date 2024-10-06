package br.com.systec.fintrack.financial.transaction.service;

import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;

import java.util.List;

public interface TransactionService {
    List<CategoryExpenseVO> findExpenseByCategory();
}
