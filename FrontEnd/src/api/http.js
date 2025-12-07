// src/api/http.js
import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 5000,
});

http.interceptors.request.use((config) => {
    const user = JSON.parse(localStorage.getItem("userInfo"));

    // 🔥 로그인이나 회원가입 같은 public API는 토큰을 붙이지 않는다
    const publicUrls = ["/auth/login", "/auth/register"];

    if (!publicUrls.includes(config.url)) {
        if (user?.token) {
            config.headers.Authorization = `Bearer ${user.token}`;
        }
    }

    return config;
});

export default http;