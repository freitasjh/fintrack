export default class CreditCard {
    constructor() {
        this.id = null;
        this.name = null;
        this.number = null;
        this.cvc = null;
        this.nameCreditCard = '';
        this.totalLimit = 0.0;
        this.availableLimit = 0.0;
        this.dueDay = '15';
        this.closingDate = '05';
        this.brand = null;
        this.interestRate;
        this.status = null;
        this.bankAccountId = null;
    }
}
