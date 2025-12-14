// src/api/http.js
import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 5000,
});

http.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    const publicUrls = ["/auth/login", "/auth/register"];

    const isPublic = publicUrls.some(url => config.url?.startsWith(url));

    if (!isPublic && token) {
        config.headers.Authorization = token.startsWith("Bearer")
            ? token
            : `Bearer ${token}`;
    }

    return config;
});

export default http;