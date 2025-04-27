import http from '@/config/axios';

export default class BudgetPlanningService {
    constructor() {
        this.endpoint = '/api/v1/budget-plannings';
    }

    async findByFilter(filter) {
        return await http.get(`${this.endpoint}`);
    }
}
