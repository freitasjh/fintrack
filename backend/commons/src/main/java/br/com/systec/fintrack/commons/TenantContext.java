package br.com.systec.fintrack.commons;

import br.com.systec.fintrack.commons.exception.TenantNotFoundException;

public class TenantContext {
    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();


    public static void add(Long tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getTenant() {
        if(CURRENT_TENANT.get() == null) {
            throw new TenantNotFoundException();
        }

        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
