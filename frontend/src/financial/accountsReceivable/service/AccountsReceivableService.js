import http from '@/config/axios';

export default class AccountsReceivableService {
    constructor() {
        this.endpoint = '/api/v1/receivable';
    }
    findById(accountReceivableId) {
        return http.get(`${this.endpoint}/${accountReceivableId}`).then((response) => response);
    }

    findByFilter(accountReceivableFiler) {
        return http.get(`${this.endpoint}/filter${accountReceivableFiler.toString()}`).then((response) => response);
    }

    save(accountReceivable) {
        if (accountReceivable.id === null) {
            return http.post(`${this.endpoint}`, accountReceivable).then((response) => response);
        }

        return this.#update(accountReceivable);
    }

    #update(accountReceivable) {
        return http.put(`${this.endpoint}`, accountReceivable).then((response) => response);
    }
}
