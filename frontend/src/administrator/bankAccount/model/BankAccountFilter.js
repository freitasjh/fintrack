export default class BankAccountFilter {
    constructor() {
        this.search = '';
        this.page = 0;
        this.limit = 30;
    }

    toString() {
        return `?limit=${this.limit}&page=${this.page}&search=${this.search}`
    }
}
