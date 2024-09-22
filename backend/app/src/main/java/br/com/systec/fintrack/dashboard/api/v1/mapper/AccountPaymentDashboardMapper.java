package br.com.systec.fintrack.dashboard.api.v1.mapper;

import br.com.systec.fintrack.dashboard.api.v1.dto.AccountPaymentDashboardDTO;
import br.com.systec.fintrack.financial.accountPayment.model.AccountPayment;

import java.util.List;

public class AccountPaymentDashboardMapper {

    private AccountPaymentDashboardMapper(){}

    public static AccountPaymentDashboardDTO toDTO(AccountPayment accountPayment) {
        AccountPaymentDashboardDTO paymentDTO = new AccountPaymentDashboardDTO();
        paymentDTO.setId(accountPayment.getId());
        paymentDTO.setAmount(accountPayment.getAmount());
        paymentDTO.setDateProcessed(accountPayment.getDateProcessed());
        paymentDTO.setDateRegister(accountPayment.getDateRegister());
        paymentDTO.setDescription(accountPayment.getDescription());
        paymentDTO.setPaymentDueDate(accountPayment.getPaymentDueDate());

        if(accountPayment.getCategory() != null) {
            paymentDTO.setCategory(accountPayment.getCategory().getDescription());
        }

        return paymentDTO;
    }

    public static List<AccountPaymentDashboardDTO> toListDTO(List<AccountPayment> listOfAccountPayment) {
        return listOfAccountPayment.stream().map(AccountPaymentDashboardMapper::toDTO).toList();
    }
}
