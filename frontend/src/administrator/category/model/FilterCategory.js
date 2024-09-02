export default class FilterCategory {
    constructor() {
        this.limit = 30;
        this.page = 0;
        this.search = '';
        this.categoryType = null;
    }

    toString() {
        let filter = `?limit=${this.limit}&page=${this.page}&search=${this.search}`;
        if (this.categoryType !== null) {
            filter = `${filter}&category_type=${this.categoryType}`;
        }
        return filter;
    }
}
