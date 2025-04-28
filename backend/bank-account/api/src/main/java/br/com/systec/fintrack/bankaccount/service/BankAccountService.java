package br.com.systec.fintrack.bankaccount.service;

import br.com.systec.fintrack.bankaccount.filter.BankAccountFilterVO;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import org.springframework.data.domain.Page;

public interface BankAccountService {

    BankAccount save(BankAccount bankAccount) throws BaseException;

    BankAccount update(BankAccount bankAccount) throws BaseException;

    BankAccount findById(Long id) throws BaseException;

    Page<BankAccount> findByFilter(BankAccountFilterVO filterVO) throws BaseException;

    Double findCurrentAccountBalance() throws BaseException;

    void updateBankAccountBalance(Double amount, Long bankAccountId, TransactionType transactionType) throws BaseException;
}
