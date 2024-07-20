import http from '@/config/axios';
export default class DashboardService {
    constructor() {
        this.endpoint = '/api/v1/dashboard';
    }

    findBalanceCurrentAccount() {
        return http.get(`${this.endpoint}/current-account-balance`).then((response) => response);
    }

    findMonthlyExpenses() {
        return http.get(`${this.endpoint}/monthly-expenses`).then((response) => response);
    }
}
