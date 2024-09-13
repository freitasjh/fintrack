export default class CreditCardFilter {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.filter = '';
        this.accountId = null;
    }

    toString() {
        let filter = `?limit=${this.limit}&page=${this.page}&paymentFilterType=${this.paymentFilterType}`;

        if (this.accountId !== null) {
            filter = filter + `&accountId=${this.accountId}`;
        }

        return filter;
    }
}
