export default class CreditCardInvoiceFilter {
    constructor() {
        this.creditCardId = null;
        this.statusType = null;
        this.limit = 30;
        this.page = 0;
        this.filter = '';
        this.sortField = '';
        this.sortOrder = '';
    }

    toString() {
        let filter = `?pageSize=${this.limit}&page=${this.page}`;

        if (this.creditCardId !== null) {
            filter = filter + `&creditCardId=${this.creditCardId}`;
        }

        if (this.statusType !== null) {
            filter = filter + `&statusType=${this.statusType}`;
        }

        if (this.sortField !== null && this.sortField !== '') {
            filter = filter + `&sortField=${this.sortField}`;
        }

        if (this.sortOrder !== null && this.sortOrder !== '') {
            filter = filter + `&sortOrder=${this.sortOrder}`;
        }

        return filter;
    }
}
