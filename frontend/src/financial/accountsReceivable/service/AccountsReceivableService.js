import http from '@/config/axios'

export default class AccountsReceivableService {
    findById(accountReceivableId) {
        return http.get(`/api/v1/accountReceivable/${accountReceivableId}`).then((response) => response);
    }

    findByFilter(accountReceivableFiler) {
        return http.get(`/api/v1/accountReceivable${accountReceivableFiler.toString()}`).then((response) => response);
    }

    save(accountReceivable) {
        if (accountReceivable.id === null) {
            return http.post("/api/v1/accountReceivable", accountReceivable).then((response) => response);
        }

        return this.#update(accountReceivable);
    }

    #update(accountReceivable) {
        return http.put('/api/v1/accountReceivable', accountReceivable).then((response) => response);
    }
}