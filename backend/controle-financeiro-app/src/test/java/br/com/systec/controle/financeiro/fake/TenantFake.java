package br.com.systec.controle.financeiro.fake;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;

public class TenantFake {

    public static Tenant fake() {
        Tenant tenant = new Tenant();
        tenant.setId(1L);
        tenant.setFederalId("05405868973");
        tenant.setName("João Henrique Freitas");

        return tenant;
    }
}
