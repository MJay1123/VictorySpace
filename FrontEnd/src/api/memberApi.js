// src/api/memberApi.js
import http from './http';

const BASE_URL = "/member";

export const memberApi = {

    /** ---------------------- Query ---------------------- **/

    findAll: () => http.get(`${BASE_URL}`),

    findById: (id) => http.get(`${BASE_URL}/${id}`),

    findByNickname: (nickname) =>
        http.get(`${BASE_URL}/nickname`, {
            params: { nickname }
        }),

    // 이메일로 검색
    findByEmail: (email) =>
        http.get(`${BASE_URL}/email`, {
            params: { email }
        }),

    // 정렬 조회
    order: (orderBy, direction) =>
        http.get(`${BASE_URL}/order`, {
            params: { orderBy, direction }
        }),

    /** ---------------------- Command ---------------------- **/

    // 회원 생성
    createMember: (memberDTO) =>
        http.post(`${BASE_URL}`, memberDTO),

    // 회원 수정 (PATCH)
    updateMember: (id, memberDTO) =>
        http.patch(`${BASE_URL}/${id}`, memberDTO),

    // 회원 삭제
    deleteMember: (id) =>
        http.delete(`${BASE_URL}/${id}`)
};

export default memberApi;
