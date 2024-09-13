import Pageable from '../../../commons/model/Pageable';
import CreditCard from '../model/creditCard';
import CreditCardService from '../service/creditCardService';

export const creditCardStore = {
    namespaced: true,
    state: {
        creditCard: new CreditCard(),
        listPageCreditCard: new Pageable()
    },
    mutations: {
        SAVE(state) {
            state.creditCard = new CreditCard();
        },
        FIND_BY_FILTER(state, response) {
            state.listPageCreditCard = response;
        }
    },
    actions: {
        async save({ commit }, creditCard) {
            const service = new CreditCardService();
            await service.save(creditCard);

            commit('SAVE');
        },
        async findByFilter({ commit }, creditCardFilter) {
            const service = new CreditCardService();
            const response = await service.findByFilter(creditCardFilter);

            commit('FIND_BY_FILTER', response.data);
        }
    }
};

export default creditCardStore;
