package br.com.systec.fintrack.tenant.fake;

import br.com.systec.fintrack.tenant.model.Tenant;

public class TenantFake {

    public static Tenant fake() {
        Tenant tenant = new Tenant();
        tenant.setId(1L);
        tenant.setFederalId("05405868973");
        tenant.setName("João Henrique Freitas");

        return tenant;
    }
}
