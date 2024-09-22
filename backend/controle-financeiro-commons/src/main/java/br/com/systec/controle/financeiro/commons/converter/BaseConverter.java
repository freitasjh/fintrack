package br.com.systec.controle.financeiro.commons.converter;

import java.util.List;

public interface BaseConverter<D, M, IPD>{

    D convertToDTO(M model);

    IPD convertTOInputDTO(M Model);

    M convertToModel(IPD inputDTO);

    List<D> convertToListDTO(List<M> listOfModel);

}
