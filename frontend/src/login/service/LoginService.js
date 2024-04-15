import http from '../../config/axios';

export default class LoginService {
    authenticate(loginModel) {
        return http.post('/api/v1/login', loginModel).then((response) => response);
    }
}
