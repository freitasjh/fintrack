import http from '@/config/axios';

export default class UserService {
    findUserById(userId) {
        return http.get(`/api/v1/users/${userId}`).then((response) => response);
    }

    saveNewAccount(user) {
        return http.post('/api/v1/users/newAccount', user).then((response) => response);
    }
}
