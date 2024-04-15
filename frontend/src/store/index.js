import Vuex from 'vuex';
import LoginService from '../login/service/LoginService';
import userModuleStore from '../administrator/user/store/userModuleStore';

export default new Vuex.Store({
    modules: {
        userModuleStore
    },
    state: {
        token: '',
        userId: ''
    },
    mutations: {
        ADD_AUTHENTICATE_INFORMATION(state, response) {
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userId', response.data.userId);
            state.userId = response.data.userId;
        }
    },
    actions: {
        async authenticate({ commit }, loginModel) {
            const loginService = new LoginService();
            const response = await loginService.authenticate(loginModel);
            if (response.status === 200) {
                commit('ADD_AUTHENTICATE_INFORMATION', response);
            }
        }
    },
    getters: {
        getToken(state) {
            var token = state.token;

            if (token === null || token === '') {
                token = localStorage.getItem('token');
            }

            return token;
        },
        getUserId(state) {
            if (state.userId === undefined || state.userId === '') {
                state.userId = localStorage.getItem('userId');
            }

            return state.userId;
        }
    }
});
