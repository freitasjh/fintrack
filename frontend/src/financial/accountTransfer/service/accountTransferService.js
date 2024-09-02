import http from '@/config/axios';

export default class AccountTransferService {
    constructor() {
        this.ENDPOINT = '/api/v1/transfers';
    }

    findByFilter(filter) {
        return http.get(`${this.ENDPOINT}/filters${filter.toString()}`).then((response) => response);
    }

    save(transfer) {
        return http.post(`${this.ENDPOINT}`, transfer).then((response) => response);
    }
}
