import axios from 'axios';

const http = axios.create({
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
});

http.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

http.interceptors.response.use(
    (response) => response,
    (error) => {
        console.log(error);
        if (error.response.status === 403 || error.response.data === undefined) {
            localStorage.clear();
            window.location.reload(true);
        } else {
            return Promise.reject(error);
        }
    }
);

export default http;
