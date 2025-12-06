// src/api/memberApi.js
import http from './http';

export const memberApi = {

    /** ---------------------- Query ---------------------- **/

    getAll() {
        return http.get('/member');
    },

    getById(id) {
        return http.get(`/member/${id}`);
    },

    findByNickname(nickname) {
        return http.get(`/member/nickname`, {
            params: { nickname }
        });
    },

    order(orderBy, direction) {
        return http.get(`/member/order`, {
            params: { orderBy, direction }
        });
    },


    /** ---------------------- Command ---------------------- **/

    create(data) {
        return http.post('/member', data);
    },

    update(id, data) {
        return http.patch(`/member/${id}`, data);
    },

    delete(id) {
        return http.delete(`/member/${id}`);
    },
};
