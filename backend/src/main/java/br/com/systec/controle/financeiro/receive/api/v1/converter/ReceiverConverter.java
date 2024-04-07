package br.com.systec.controle.financeiro.receive.api.v1.converter;


import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.converter.BaseConverter;
import br.com.systec.controle.financeiro.receive.api.v1.dto.ReceiveDTO;
import br.com.systec.controle.financeiro.receive.api.v1.dto.ReceiveInputDTO;
import br.com.systec.controle.financeiro.receive.model.Receive;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceiverConverter implements BaseConverter<ReceiveDTO, Receive, ReceiveInputDTO> {

    @Override
    public ReceiveDTO convertToDTO(Receive model) {
        ReceiveDTO receiveDTO = new ReceiveDTO();
        receiveDTO.setId(model.getId());
        receiveDTO.setDateReceiver(model.getDateReceiver());
        receiveDTO.setDateRegister(model.getDateRegister());
        receiveDTO.setCategory(model.getCategory().getDescription());
        receiveDTO.setDescription(model.getDescription());

        return receiveDTO;
    }

    @Override
    public ReceiveInputDTO convertTOInputDTO(Receive model) {
        ReceiveInputDTO receiveInputDTO = new ReceiveInputDTO();
        receiveInputDTO.setId(model.getId());
        receiveInputDTO.setDescription(model.getDescription());
        receiveInputDTO.setAmount(model.getAmount());
        receiveInputDTO.setDateReceiver(model.getDateReceiver());
        receiveInputDTO.setDateRegister(model.getDateRegister());
        receiveInputDTO.setCategoryId(model.getCategory().getId());
        receiveInputDTO.setCategoryDescription(model.getCategory().getDescription());

        return receiveInputDTO;
    }

    @Override
    public Receive convertToModel(ReceiveInputDTO inputDTO) {
        Receive receive = new Receive();
        receive.setId(inputDTO.getId());
        receive.setDescription(inputDTO.getDescription());
        receive.setAccountId(inputDTO.getAccountId());
        receive.setDateReceiver(inputDTO.getDateReceiver());
        receive.setDateRegister(inputDTO.getDateRegister());
        receive.setAmount(inputDTO.getAmount());
        receive.setTenantId(TenantContext.getTenant());
        return receive;
    }

    @Override
    public List<ReceiveDTO> convertToListDTO(List<Receive> listOfModel) {
        return listOfModel.stream().map(this::convertToDTO).toList();
    }
}
