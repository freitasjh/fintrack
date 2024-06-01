export default class Pageable {
    constructor() {
        this.totalPage = 0;
        this.totalElements = 0;
        this.size = 0;
        this.last = true;
        this.first = true;
        this.empty = true;
        this.numberOfElements = 0;
    }
}