// src/api/http.js
import axios from 'axios';

const http = axios.create({
    baseURL: 'http://localhost:8080/api', 
    timeout: 5000
});

// 요청/응답 인터셉터 추가 가능 (토큰 등 필요시)
http.interceptors.request.use(config => {
    const user = JSON.parse(localStorage.getItem('userInfo'));
    if (user?.token) {
        config.headers.Authorization = `Bearer ${user.token}`;
    }
    return config;
});

export default http;
