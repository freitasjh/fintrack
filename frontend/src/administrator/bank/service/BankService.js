import http from '@/config/axios';

export default class Bank {
    findBanks() {
        return http.get('api/v1/banks').then((response) => response);
    }
}
