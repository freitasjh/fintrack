import http from '@/config/axios';

export default class CreditCardService {
    constructor() {
        this.endpoint = '/api/v1/credit-cards';
    }
    save(creditCard) {
        if (creditCard.id === null) {
            return http.post(this.endpoint, creditCard).then((response) => response);
        }

        return this.#update(creditCard);
    }

    #update(creditCard) {
        return http.put(this.endpoint, creditCard).then((response) => response);
    }

    findById(id) {}

    findByFilter(filter) {
        console.log(filter.toString());
        return http.get(`${this.endpoint}/filter${filter.toString()}`).then((response) => response);
    }
}
