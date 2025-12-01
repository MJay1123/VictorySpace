// src/api/commentApi.js
import http from './http';

export const commentApi = {
    
    /** ---------------------- Query API ---------------------- **/

    getAllComments() {
        return http.get('/comments');
    },

    getCommentById(id) {
        return http.get(`/comments/${id}`);
    },

    getCommentsByVoteId(voteId) {
        return http.get(`/comments/vote/${voteId}`);
    },

    getCommentsByMemberId(memberId) {
        return http.get(`/comments/member/${memberId}`);
    },


    /** ---------------------- Command API ---------------------- **/

    createComment(data) {
        return http.post('/comment', data);
    },

    updateComment(id, data) {
        return http.patch(`/comment/${id}`, data);
    },

    deleteComment(id) {
        return http.delete(`/comment/${id}`);
    }
};
