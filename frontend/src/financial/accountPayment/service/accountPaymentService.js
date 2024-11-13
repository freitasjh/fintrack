import http from '@/config/axios';

export default class AccountPaymentService {
    constructor() {
        this.endpoint = '/api/v1/payments';
    }

    save(accountPayment) {
        if (accountPayment.id !== null) {
            return this.#update(accountPayment);
        }
        return http.post(`${this.endpoint}`, accountPayment).then((response) => response);
    }

    #update(accountPayment) {
        return http.put(`${this.endpoint}`, accountPayment).then((response) => response);
    }

    findByFilter(filter) {
        return http.get(`${this.endpoint}/filters${filter.toString()}`).then((response) => response);
    }
    registerPayment(register) {
        console.log(register);
        return http.put(`${this.endpoint}/register-payment/${register.id}/${register.dateRegister}`).then((response) => response);
    }
}
