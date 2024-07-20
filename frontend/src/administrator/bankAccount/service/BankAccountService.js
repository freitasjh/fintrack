import http from '@/config/axios';

export default class BankAccountService {
    constructor() {
        this.endpoint = '/api/v1/bank-accounts';
    }

    findById(bankAccountId) {
        return http.get(`${this.endpoint}/${bankAccountId}`).then((response) => response);
    }

    findByFilter(bankAccountFilter) {
        return http.get(`${this.endpoint}/filter${bankAccountFilter.toString()}`).then((response) => response);
    }

    save(bankAccount) {
        if (bankAccount.id === null) {
            return http.post(`${this.endpoint}`, bankAccount).then((response) => response);
        }
        return this.#update(bankAccount);
    }

    #update(bankAccount) {
        return http.put(`${this.endpoint}`, bankAccount).then((response) => response);
    }
}
