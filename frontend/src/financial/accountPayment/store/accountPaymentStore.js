import Pageable from '../../../commons/model/Pageable';
import AccountPayment from '../model/accountPayment';
import AccountPaymentService from '../service/accountPaymentService';

export const accountPaymentStore = {
    namespaced: true,
    state: {
        accountPayment: new AccountPayment(),
        listPageAccountPayment: new Pageable()
    },
    mutations: {
        RETURN_PAGE_ACCOUNT(state, response) {
            state.listPageAccountPayment = response.data;
        },
        RETURN_ACCOUNT_PAYMENT(state, response) {},
        SAVE(state) {
            state.accountPayment = new AccountPayment();
        }
    },
    actions: {
        async findByFilter({ commit }, accountPaymentFilter) {
            const service = new AccountPaymentService();
            const response = await service.findByFilter(accountPaymentFilter);

            commit('RETURN_PAGE_ACCOUNT', response);
        },
        async findById({ commit }, accountPaymentId) {
            const service = new AccountPaymentService();
            const response = await service.findById(accountPaymentId);

            commit('RETURN_ACCOUNT_PAYMENT', response);
        },
        async save({ commit }, accountPayment) {
            const service = new AccountPaymentService();
            await service.save(accountPayment);

            commit('SAVE');
        }
    }
};

export default accountPaymentStore;
