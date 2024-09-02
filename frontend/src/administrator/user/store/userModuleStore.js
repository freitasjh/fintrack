/* eslint-disable no-unused-vars */
import UserService from '../service/UserService';

export const userModuleStore = {
    namespaced: true,
    state: {
        user: null
    },
    mutations: {
        USER_INFORMATION(state, response) {
            state.user = response.data;
        }
    },
    actions: {
        async findUserById({ commit }, userId) {
            const userService = new UserService();
            const response = await userService.findUserById(userId);

            if (response.status === 200) {
                commit('USER_INFORMATION', response);
            }
        },
        async saveNewAccount({ commit }, user) {
            const userService = new UserService();
            await userService.saveNewAccount(user);
        }
    },
    getters: {
        getUser({ state }) {
            // if (state.user === null) {
            //     dispatch('fundUserById', localStorage.getItem('userId'));
            // }

            return state.user;
        }
    }
};

export default userModuleStore;
