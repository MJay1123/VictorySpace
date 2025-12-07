// src/api/commentApi.js
import http from './http';

const BASE_URL = '/comment';

export const commentApi = {
    
    /** ---------------------- Query API ---------------------- **/

    findAll: () => http.get(`${BASE_URL}`),

    findById: (id) => http.get(`${BASE_URL}/${id}`),

    findByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),

    findByMemberId: (memberId) => http.get(`${BASE_URL}/member/${memberId}`),

    /** ---------------------- Command API ---------------------- **/

    createComment: (commentDTO) => http.post(`${BASE_URL}`, commentDTO),

    updateComment: (id, commentDTO) => http.patch(`${BASE_URL}/${id}`, commentDTO),

    deleteComment: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default commentApi;