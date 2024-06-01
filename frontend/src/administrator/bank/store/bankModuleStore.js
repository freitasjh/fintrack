import BankService from '@/administrator/bank/service/BankService';
export const bankModuleStore = {
    namespaced: true,
    state: {
        listOfBank: []
    },
    mutations: {
        LIST_BANK(state, response) {
            state.listOfBank = response.data;
        }
    },
    actions: {
        async findBank({ commit }) {
            const bankService = new BankService();
            const response = await bankService.findAllBank();
            if (response.status === 200) {
                commit('LIST_BANK', response);
            }
        }
    },
    getters: {
        getListOfBank({ state }) {
            return state.listOfBank;
        }
    }
};

export default bankModuleStore;
