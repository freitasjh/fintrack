package br.com.systec.controle.financeiro.commons;

public class TenantContext {
    private static final ThreadLocal<Long> tenantCache = new ThreadLocal<>();


    public static void add(Long tenantId) {
        tenantCache.set(tenantId);
    }

    public static Long getTenant() {
        return tenantCache.get();
    }
}
