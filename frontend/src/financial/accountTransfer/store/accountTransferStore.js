import Pageable from '../../../commons/model/Pageable';
import AccountTransfer from '../model/accountTransfer';
import AccountTransferService from '../service/accountTransferService';

export const accountTransferStore = {
    namespaced: true,
    state: {
        accountTransfer: new AccountTransfer(),
        listPageAccountTransfer: new Pageable()
    },
    mutations: {
        SAVE(state) {
            state.accountTransfer = new AccountTransfer();
        },
        FIND_BY_FILTER(state, response) {
            state.listPageAccountTransfer = response;
        }
    },
    actions: {
        async save({ commit }, accountTransfer) {
            const service = new AccountTransferService();
            await service.save(accountTransfer);

            commit('SAVE');
        },
        async findByFilter({ commit }, filter) {
            const service = new AccountTransferService();
            const response = await service.findByFilter(filter);

            commit('FIND_BY_FILTER', response.data);
        }
    }
};

export default accountTransferStore;
