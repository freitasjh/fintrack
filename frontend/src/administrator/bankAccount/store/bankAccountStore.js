import Pageable from "../../../commons/model/Pageable";
import BankAccountService from "../service/BankAccountService";

export const bankAccountStore = {
   namespaced: true,
    state: {
        bankAccount: null,
        pageOfBankAccount: new Pageable(),
    },
    mutations: {
        RETURN_LIST_BANK_ACCOUNT(state, response) {
            state.pageOfBankAccount = response.data;
        },
        RETURN_BANK_ACCOUNT(state, response) {
            state.bankAccount = response.data;
        }
    },
    actions: {
        async findBankAccountByFilter({ commit }, filterBankAccount) {
            const bankAccountService = new BankAccountService();
            const response = await bankAccountService.findByFilter(filterBankAccount);

            commit('RETURN_LIST_BANK_ACCOUNT', response);
        },
        async saveBankAccount({ commit }, bankAccount) {
            const bankAccountService = new BankAccountService();
            await bankAccountService.save(bankAccount);
        },
        async findBankAccount({ commit }, bankAccountId) {
            const bankAccountService = new BankAccountService();
            const response = await bankAccountService.findById(bankAccountId);

            commit('RETURN_BANK_ACCOUNT', response);
        }
    },
}

export default bankAccountStore;