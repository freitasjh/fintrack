import PaginatedResult from '../../../commons/model/paginatedResult';
import BudgetPlanning from '../model/budgetPlanning';
import BudgetPlanningService from '../service/budgetPlanningService';

export const budgetPlanningStore = {
    namespaced: true,
    state: {
        budgetPlanning: new BudgetPlanning(),
        listOfPageBudgetPlanning: new PaginatedResult()
    },
    mutations: {
        setResultFindBudgetPlanning(state, response) {
            state.listOfPageBudgetPlanning = response;
        }
    },
    actions: {
        async findByFilter({ commit }, filter) {
            const service = new BudgetPlanningService();
            const response = await service.findByFilter(filter);

            commit('setResultFindBudgetPlanning', response.data);
        }
    }
};
