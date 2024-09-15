export default class CreditCardTransactionFilter {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.filter = '';
    }

    toString() {
        let filter = `?limit=${this.limit}&page=${this.page}`;

        return filter;
    }
}
