// import Pageable from '../../../../commons/model/Pageable';
import PaginatedResult from '../../../../commons/model/paginatedResult';
import CreditCardTransaction from '../model/creditCardTransaction';
import CreditCardTransactionService from '../service/creditCardTransactionService';

export const creditCardTransactionStore = {
    namespaced: true,
    state: {
        creditCardTransaction: new CreditCardTransaction(),
        listOfPageTransaction: new PaginatedResult()
    },
    mutations: {
        clearInformation(state) {
            state.creditCardTransaction = new CreditCardTransaction();
        },
        setCreditCard(state, creditCardId) {
            state.creditCardTransaction.creditCardId = creditCardId;
        },
        setResultFindTransaction(state, response) {
            state.listOfPageTransaction = response;
        }
    },
    actions: {
        async save({ commit, state }) {
            const service = new CreditCardTransactionService();
            await service.save(state.creditCardTransaction);

            commit('clearInformation');
        },
        async findByFilter({ commit }, filter) {
            const service = new CreditCardTransactionService();
            const response = await service.findByFilter(filter);

            commit('setResultFindTransaction', response.data);
        },
        async findById({ commit }, id) {}
    }
};

export default creditCardTransactionStore;
