package br.com.systec.controle.financeiro.commons;

public class TenantContext {
    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();


    public static void add(Long tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getTenant() {
        return CURRENT_TENANT.get();
    }
}
