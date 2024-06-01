import http from '@/config/axios';

export default class Bank {
    findAllBank() {
        return http.get('api/v1/banks?limit=200').then((response) => response);
    }
}
