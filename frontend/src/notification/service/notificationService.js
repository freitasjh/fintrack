import http from '@/config/axios';

export default class NotificationService {
    constructor() {
        this.endpoint = '/api/v1/notifications';
    }

    findTotalNotificationNotVisualized(userId) {
        return http.get(`${this.endpoint}/count/${userId}`).then((response) => response);
    }

    findNotificationByUser(userId) {
        return http.get(`${this.endpoint}/${userId}`).then((response) => response);
    }

    updateAllVisualized(userId) {
        return http.put(`${this.endpoint}/visualized/${userId}`).then((response) => response);
    }
}
