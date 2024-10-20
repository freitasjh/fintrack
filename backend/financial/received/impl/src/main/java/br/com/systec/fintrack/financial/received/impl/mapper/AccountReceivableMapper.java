package br.com.systec.fintrack.financial.received.impl.mapper;

import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class AccountReceivableMapper {

    public static AccountReceivableVO toVO(AccountReceivable accountReceivable) {
        AccountReceivableVO accountReceivableVO = new AccountReceivableVO();
        accountReceivableVO.setId(accountReceivable.getId());
        accountReceivableVO.setBankAccount(accountReceivable.getBankAccount());
        accountReceivableVO.setAmount(accountReceivable.getAmount());
        accountReceivableVO.setDescription(accountReceivable.getDescription());
        accountReceivableVO.setDateRegister(accountReceivable.getDateRegister());
        accountReceivableVO.setCategoryTransactionType(accountReceivable.getCategoryTransactionType());
        accountReceivableVO.setBankAccount(accountReceivable.getBankAccount());
        accountReceivableVO.setDateProcessed(accountReceivable.getDateProcessed());
        accountReceivableVO.setTransactionType(accountReceivable.getTransactionType());

        return accountReceivableVO;
    }

    public static AccountReceivable toEntity(AccountReceivableVO accountReceivableVO) {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setId(accountReceivableVO.getId());
        accountReceivable.setBankAccount(accountReceivableVO.getBankAccount());
        accountReceivable.setAmount(accountReceivableVO.getAmount());
        accountReceivable.setDescription(accountReceivableVO.getDescription());
        accountReceivable.setDateRegister(accountReceivableVO.getDateRegister());
        accountReceivable.setCategoryTransactionType(accountReceivableVO.getCategoryTransactionType());
        accountReceivable.setBankAccount(accountReceivableVO.getBankAccount());
        accountReceivable.setDateProcessed(accountReceivableVO.getDateProcessed());
        accountReceivable.setTransactionType(accountReceivableVO.getTransactionType());

        return accountReceivable;
    }

    public static List<AccountReceivableVO> toListVO(List<AccountReceivable> listOfAccountReceivable) {
        return listOfAccountReceivable.stream().map(AccountReceivableMapper::toVO).collect(Collectors.toList());
    }

    public static Page<AccountReceivableVO> toPageVO(Page<AccountReceivable> pageAccountReceivable) {
        return pageAccountReceivable.map(AccountReceivableMapper::toVO);
    }
}
