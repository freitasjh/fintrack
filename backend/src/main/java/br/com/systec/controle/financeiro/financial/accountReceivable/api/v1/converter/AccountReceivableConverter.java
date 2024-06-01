package br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.converter;


import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.converter.BaseConverter;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableDTO;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;


public class AccountReceivableConverter  {

    public static AccountReceivableDTO convertToDTO(AccountReceivable model) {
        AccountReceivableDTO receiveDTO = new AccountReceivableDTO();
        receiveDTO.setId(model.getId());
        receiveDTO.setDateReceiver(model.getDateReceiver());
        receiveDTO.setDateRegister(model.getDateRegister());
        receiveDTO.setDescription(model.getDescription());

        return receiveDTO;
    }


    public static AccountReceivableInputDTO convertTOInputDTO(AccountReceivable model) {
        AccountReceivableInputDTO receiveInputDTO = new AccountReceivableInputDTO();
        receiveInputDTO.setId(model.getId());
        receiveInputDTO.setDescription(model.getDescription());
        receiveInputDTO.setAmount(model.getAmount());
        receiveInputDTO.setDateReceiver(model.getDateReceiver());
        receiveInputDTO.setDateRegister(model.getDateRegister());

        return receiveInputDTO;
    }


    public static AccountReceivable convertToModel(AccountReceivableInputDTO inputDTO) {
        AccountReceivable receive = new AccountReceivable();
        receive.setId(inputDTO.getId());
        receive.setDescription(inputDTO.getDescription());
        receive.setAccountId(inputDTO.getAccountId());
        receive.setDateReceiver(inputDTO.getDateReceiver());
        receive.setDateRegister(inputDTO.getDateRegister());
        receive.setAmount(inputDTO.getAmount());
        receive.setTenantId(TenantContext.getTenant());

        return receive;
    }


    public static List<AccountReceivableDTO> convertToListDTO(List<AccountReceivable> listOfModel) {
        return listOfModel.stream().map(AccountReceivableConverter::convertToDTO).toList();
    }

    public static Page<AccountReceivableDTO> convertToPageDTO(Page<AccountReceivable> pageOfAccountReceivable) {
        return pageOfAccountReceivable.map(AccountReceivableConverter::convertToDTO);
    }
}
