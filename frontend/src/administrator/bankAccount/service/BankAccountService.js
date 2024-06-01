import http from '@/config/axios';

export default class BankAccountService {
    findById(bankAccountId) {
        return http.get(`/api/v1/bankAccounts/${bankAccountId}`).then((response) => response);
    }

    findByFilter(bankAccountFilter) {
        return http.get(`/api/v1/bankAccounts/filter${bankAccountFilter.toString()}`).then((response) => response);
    }

    save(bankAccount) {
        if (bankAccount.id === null) {
            return http.post('/api/v1/bankAccounts', bankAccount).then((response) => response);
        }
        return this.#update(bankAccount);
    }

    #update(bankAccount) {
        return http.put('/api/v1/bankAccounts', bankAccount).then((response) => response);
    }
}
