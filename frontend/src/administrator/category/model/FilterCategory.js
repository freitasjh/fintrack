export default class FilterCategory {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.search = "";
    }

    toString() {
        return `?limit=${this.limit}&page=${this.page}&search=${this.search}`;
    }
}