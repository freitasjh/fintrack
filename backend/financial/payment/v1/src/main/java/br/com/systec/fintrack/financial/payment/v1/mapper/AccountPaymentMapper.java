package br.com.systec.fintrack.financial.payment.v1.mapper;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentDTO;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentInputDTO;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountPaymentMapper {

    private AccountPaymentMapper(){}

    public static AccountPayment toModel(AccountPaymentInputDTO inputDTO) {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setId(inputDTO.getId());
        accountPayment.setDescription(inputDTO.getDescription());
        accountPayment.setAmount(inputDTO.getAmount());
        accountPayment.setProcessed((inputDTO.getDateProcessed() != null));
        accountPayment.setDateProcessed(inputDTO.getDateProcessed());
        accountPayment.setDateRegister(inputDTO.getDateRegister());
        accountPayment.setBankAccount(new BankAccount(inputDTO.getBankAccountId()));
        accountPayment.setTransactionType(TransactionType.EXPENSE);
        accountPayment.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        accountPayment.setTenantId(TenantContext.getTenant());
        accountPayment.setPaymentDueDate(inputDTO.getPaymentDueDate());

        if(inputDTO.getDateRegister() == null){
            accountPayment.setDateRegister(new Date());
        }

        if(inputDTO.getCategoryId() != null) {
            accountPayment.setCategory(new Category(inputDTO.getCategoryId()));
        }

        return accountPayment;
    }

    public static AccountPaymentDTO toDTO(AccountPayment accountPayment) {
        AccountPaymentDTO paymentDTO = new AccountPaymentDTO();
        paymentDTO.setId(accountPayment.getId());
        paymentDTO.setDescription(accountPayment.getDescription());
        paymentDTO.setAmount(accountPayment.getAmount());
        paymentDTO.setBankAccount(accountPayment.getBankAccount().getDescription());
        paymentDTO.setDateProcessed(accountPayment.getDateProcessed());
        paymentDTO.setDateRegister(accountPayment.getDateRegister());
        paymentDTO.setPaymentDueDate(accountPayment.getPaymentDueDate());

        if(accountPayment.getCategory() != null) {
            paymentDTO.setCategory(accountPayment.getCategory().getDescription());
        }

        return paymentDTO;
    }

    public static AccountPaymentInputDTO toInputDTO(AccountPayment accountPayment) {
        AccountPaymentInputDTO inputDTO = new AccountPaymentInputDTO();
        inputDTO.setId(accountPayment.getId());
        inputDTO.setDescription(accountPayment.getDescription());
        inputDTO.setProcessed(accountPayment.isProcessed());
        inputDTO.setDateProcessed(accountPayment.getDateProcessed());
        inputDTO.setDateRegister(accountPayment.getDateRegister());
        inputDTO.setBankAccountId(accountPayment.getBankAccount().getId());
        inputDTO.setAmount(accountPayment.getAmount());
        inputDTO.setPaymentDueDate(accountPayment.getPaymentDueDate());


        if(inputDTO.getDateRegister() != null) {
            inputDTO.setDateRegister(new Date());
        }

        if(accountPayment.getCategory() != null) {
            inputDTO.setCategoryId(accountPayment.getCategory().getId());
        }

        return inputDTO;
    }

    public static List<AccountPaymentDTO> toListDTO(List<AccountPayment> listOfPayment) {
        return listOfPayment.stream().map(AccountPaymentMapper::toDTO).collect(Collectors.toList());
    }

    public static Page<AccountPaymentDTO> toPageDTO(Page<AccountPayment> pageOfPayment) {
        return pageOfPayment.map(AccountPaymentMapper::toDTO);
    }
}
