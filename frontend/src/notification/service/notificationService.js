import http from '@/config/axios';

export default class NotificationService {
    constructor() {
        this.endpoint = '/api/v1/notifications';
    }

    findTotalNotificationNotVisualized(userId) {
        return http.get(`${this.endpoint}/${userId}`).then((response) => response);
    }
}
