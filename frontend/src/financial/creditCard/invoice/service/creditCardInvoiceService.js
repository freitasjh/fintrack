import http from '@/config/axios';

export default class CreditCardInvoiceService {
    constructor() {
        this.endpoint = '/api/v1/credit-card/invoices';
    }

    findByFilter(filter) {
        return http.get(`${this.endpoint}`).then((response) => response);
    }
}
