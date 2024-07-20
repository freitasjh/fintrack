import Pageable from '../../../commons/model/Pageable';
import AccountReceivable from '../model/AccountReceivable';
import AccountsReceivableService from '../service/AccountsReceivableService';

export const accountReceivableStore = {
    namespaced: true,
    state: {
        accountReceivable: null,
        listPageAccountReceivable: new Pageable()
    },
    mutations: {
        RETURN_PAGE_ACCOUNT(state, response) {
            state.listPageAccountReceivable = response.data;
        },
        RETURN_ACCOUNT_RECEIVABLE(state, response) {
            state.accountReceivable = response.data;
        },
        SAVE(state) {
            state.accountReceivable = new AccountReceivable();
        }
    },
    actions: {
        async findByFilter({ commit }, accountReceivableFilter) {
            const service = new AccountsReceivableService();
            const response = await service.findByFilter(accountReceivableFilter);

            commit('RETURN_PAGE_ACCOUNT', response);
        },
        async findById({ commit }, accountReceivableId) {
            const service = new AccountsReceivableService();
            const response = await service.findById(accountReceivableId);

            commit('RETURN_ACCOUNT_RECEIVABLE', response);
        },
        async save({ commit }, accountReceivable) {
            const service = new AccountsReceivableService();
            await service.save(accountReceivable);
            commit('SAVE');
        }
    }
};

export default accountReceivableStore;
