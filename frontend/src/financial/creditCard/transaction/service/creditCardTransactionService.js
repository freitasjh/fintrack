import http from '@/config/axios';

export default class CreditCardTransactionService {
    constructor() {
        this.endpoint = '/api/v1/credit-cards/transactions';
    }

    save(transaction) {
        return http.post(`${this.endpoint}`, transaction).then((response) => response);
    }

    findByFilter(filter) {
        return http.get(`${this.endpoint}/filter${filter.toString()}`).then((response) => response);
    }
}
