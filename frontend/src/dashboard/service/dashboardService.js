import http from '@/config/axios';
export default class DashboardService {
    constructor() {
        this.endpoint = '/api/v1/dashboards';
    }

    findBalanceCurrentAccount() {
        return http.get(`${this.endpoint}/current-account-balance`).then((response) => response);
    }

    findMonthlyExpenses() {
        return http.get(`${this.endpoint}/monthly-expenses`).then((response) => response);
    }

    findRecentPayment() {
        return http.get(`${this.endpoint}/last-payment`).then((response) => response);
    }

    findExpenseByCategory() {
        return http.get(`${this.endpoint}/category-expense`).then((response) => response);
    }

    findPaymentNotProcessed() {
        return http.get(`${this.endpoint}/payment-not-processed`).then((response) => response);
    }

    findPaymentOpen() {
        return http.get(`${this.endpoint}/payment-open`).then((response) => response);
    }

    findCreditCardInvoiceAmountOpen() {
        return http.get(`${this.endpoint}/invoice-amount-open`).then((response) => response);
    }
}
