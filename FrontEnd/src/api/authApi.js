// src/api/authApi.js
import http from "./http";

const BASE_URL = "/auth";

export const authApi = {

    // 회원가입
    register: (authDto) =>
        http.post(`${BASE_URL}/register`, authDto, {
            headers: { Authorization: "" }
        }),

    // 로그인
    login: (email, password) =>
        http.post(
            `${BASE_URL}/login`,
            new URLSearchParams({
                email: email,
                password: password,
            }),
            {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    Authorization: ""
                },
            }
        ),
};

export default authApi;