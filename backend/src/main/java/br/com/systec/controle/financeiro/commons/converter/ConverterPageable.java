package br.com.systec.controle.financeiro.commons.converter;

import org.springframework.data.domain.Page;

public interface ConverterPageable<D, M, IPD> extends BaseConverter<D,M,IPD>{

    Page<D> convertePage(Page<M> pageModel);

}
