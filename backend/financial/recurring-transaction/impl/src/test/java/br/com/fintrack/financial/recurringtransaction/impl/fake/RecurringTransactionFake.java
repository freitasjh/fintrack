package br.com.fintrack.financial.recurringtransaction.impl.fake;

import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;

public class RecurringTransactionFake {

    public static RecurringTransaction toFake() {
        RecurringTransaction recurringTransaction = new RecurringTransaction();
        recurringTransaction.setId(1L);
        recurringTransaction.setTenantId(1L);
        recurringTransaction.setTransactionFixed(true);
        recurringTransaction.setTransactionType(TransactionType.INCOMING);
        recurringTransaction.setTotalInstallments(10);
        recurringTransaction.setCurrentInstallments(1);

        return recurringTransaction;
    }

    public static RecurringTransactionVO toFakeVO() {
        RecurringTransactionVO recurringTransaction = new RecurringTransactionVO();
        recurringTransaction.setId(1L);
        recurringTransaction.setTenantId(1L);
        recurringTransaction.setTransactionFixed(true);
        recurringTransaction.setTransactionType(TransactionType.INCOMING);
        recurringTransaction.setTotalInstallments(10);
        recurringTransaction.setCurrentInstallments(1);

        return recurringTransaction;
    }
}
