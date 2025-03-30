import http from '@/config/axios';

export default class CreditCardInvoiceService {
    constructor() {
        this.endpoint = '/api/v1/credit-card/invoices';
    }

    async findByFilter(filter) {
        return await http.get(`${this.endpoint}${filter.toString()}`);
    }

    async registerPayment(creditCardInvoiceRegister) {
        return await http.put(`${this.endpoint}/register-payment`, creditCardInvoiceRegister);
    }

    async findInstallmentByInvoiceId(invoiceId) {
        return await http.get(`${this.endpoint}/installment/${invoiceId}`);
    }
}
