export default class AccountReceivableFilter {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.filter = '';
        this.accountId = null;
        this.dateProcessed = null;
    }

    toString() {
        let filter = `?limit=${this.limit}&page=${this.page}`;

        if (this.accountId !== null) {
            filter = filter + `&accountId=${this.accountId}`;
        }

        if (this.dateProcessed !== null) {
            filter = filter + `&dateProcessed=${this.dateProcessed}`;
        }

        return filter;
    }
}
