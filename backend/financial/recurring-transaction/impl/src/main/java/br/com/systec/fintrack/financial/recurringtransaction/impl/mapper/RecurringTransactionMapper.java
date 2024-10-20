package br.com.systec.fintrack.financial.recurringtransaction.impl.mapper;

import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;

public class RecurringTransactionMapper {

    public static RecurringTransactionVO toVO(RecurringTransaction recurringTransaction) {
        RecurringTransactionVO recurringTransactionVO = new RecurringTransactionVO();
        recurringTransactionVO.setId(recurringTransaction.getId());
        recurringTransactionVO.setTransactionId(recurringTransaction.getTransactionId());
        recurringTransactionVO.setTransactionFixed(recurringTransaction.isTransactionFixed());
        recurringTransactionVO.setTransactionFixed(recurringTransaction.isTransactionFixed());
        recurringTransactionVO.setFrequencyType(recurringTransaction.getFrequencyType());
        recurringTransactionVO.setCurrentInstallments(recurringTransaction.getCurrentInstallments());
        recurringTransactionVO.setTotalInstallments(recurringTransaction.getTotalInstallments());
        recurringTransactionVO.setTransactionType(recurringTransaction.getTransactionType());
        recurringTransactionVO.setTenantId(recurringTransaction.getTenantId());

        return recurringTransactionVO;
    }

    public static RecurringTransaction toEntity(RecurringTransactionVO recurringTransactionVO) {
        RecurringTransaction recurringTransaction = new RecurringTransaction();
        recurringTransaction.setId(recurringTransactionVO.getId());
        recurringTransaction.setTransactionId(recurringTransactionVO.getTransactionId());
        recurringTransaction.setTransactionFixed(recurringTransactionVO.isTransactionFixed());
        recurringTransaction.setTransactionFixed(recurringTransactionVO.isTransactionFixed());
        recurringTransaction.setFrequencyType(recurringTransactionVO.getFrequencyType());
        recurringTransaction.setCurrentInstallments(recurringTransactionVO.getCurrentInstallments());
        recurringTransaction.setTotalInstallments(recurringTransactionVO.getTotalInstallments());
        recurringTransaction.setTransactionType(recurringTransactionVO.getTransactionType());
        recurringTransaction.setTenantId(recurringTransactionVO.getTenantId());

        return recurringTransaction;
    }
}
