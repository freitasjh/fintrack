import Pageable from "../../../commons/model/Pageable";
import AccountsReceivableService from "../service/AccountsReceivableService";

export const accountReceivableStore = {
    namespaced: true,
    state: {
        accountReceivable: null,
        listPageAccountReceivable: new Pageable(),
    },
    mutations: {
        RETURN_PAGE_ACCOUNT(state, response) {
            state.listPageAccountReceivable = response.data;
        },
        RETURN_ACCOUNT_RECEIVABLE(state, response) {
            state.accountReceivable = response.data;
        }
    },
    actions: {
        async finByFilter({ commit }, accountReceivableFilter) {
            const service = new AccountsReceivableService();
            const response = await service.findByFilter(accountReceivableFilter);

            commit('RETURN_PAGE_ACCOUNT', response);
        },
        async findById({ commit }, accountReceivableId) {
            const service = new AccountsReceivableService();
            const response = await service.findById(accountReceivableId);

            commit('RETURN_ACCOUNT_RECEIVABLE', response);
        }
    }
}

export default accountReceivableStore;