// src/api/likesApi.js
import http from './http';

export const likesApi = {

    /** ---------------------- Query ---------------------- **/

    getAll() {
        return http.get('/likes');
    },

    getById(id) {
        return http.get(`/likes/${id}`);
    },

    getByVoteId(voteId) {
        return http.get(`/likes/vote/${voteId}`);
    },

    getByMemberId(memberId) {
        return http.get(`/likes/member/${memberId}`);
    },


    /** ---------------------- Command ---------------------- **/

    create(data) {
        return http.post('/likes', data);
    },

    delete(id) {
        return http.delete(`/likes/${id}`);
    }
};
