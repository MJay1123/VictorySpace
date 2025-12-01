// src/services/commentService.js
import { commentApi } from '@/api/commentApi';

export const commentService = {

    async fetchComments() {
        const res = await commentApi.getAllComments();
        return res.data;
    },

    async fetchComment(id) {
        const res = await commentApi.getCommentById(id);
        return res.data;
    },

    async fetchCommentsByVote(voteId) {
        const res = await commentApi.getCommentsByVoteId(voteId);
        return res.data;
    },

    async fetchCommentsByMember(memberId) {
        const res = await commentApi.getCommentsByMemberId(memberId);
        return res.data;
    },

    async create(dto) {
        const res = await commentApi.createComment(dto);
        return res.data;
    },

    async update(id, dto) {
        const res = await commentApi.updateComment(id, dto);
        return res.data;
    },

    async remove(id) {
        await commentApi.deleteComment(id);
        return true;
    }
};
