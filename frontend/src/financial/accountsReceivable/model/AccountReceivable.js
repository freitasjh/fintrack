export default class AccountReceivable {
    constructor() {
        this.id = null;
        this.bankAccountId = null;
        this.amount = null;
        this.description = '';
        this.dateProcessed = null;
        this.dateReceiver = null;
        this.bankAccountDescription = null;
        this.recurringTransaction = false;
        this.recurringTransactionFixed = false;
        this.totalInstallments = 0;
        this.frequencyType = '';
    }
}
