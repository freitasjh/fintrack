export default class AccountTransferFilter {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.filter = '';
        this.bankFromId = null;
        this.bankToId = null;
    }

    toString() {
        let filter = `?limit=${this.limit}&page=${this.page}&paymentFilterType=${this.paymentFilterType}`;

        if (this.accountId !== null) {
            filter = filter + `&accountId=${this.accountId}`;
        }

        if (this.bankFromId !== null) {
            filter = filter + `&bankaccountfromid=${this.bankFromId}`;
        }

        if (this.bankToId !== null) {
            filter = filter + `&bankaccounttoid=${this.bankToId}`;
        }

        return filter;
    }
}
