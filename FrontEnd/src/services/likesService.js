// src/services/likesService.js
import { likesApi } from '@/api/likesApi';

export const likesService = {

    async fetchAll() {
        const res = await likesApi.getAll();
        return res.data;
    },

    async fetch(id) {
        const res = await likesApi.getById(id);
        return res.data;
    },

    async fetchByVote(voteId) {
        const res = await likesApi.getByVoteId(voteId);
        return res.data;
    },

    async fetchByMember(memberId) {
        const res = await likesApi.getByMemberId(memberId);
        return res.data;
    },

    async create(dto) {
        const res = await likesApi.create(dto);
        return res.data;
    },

    async remove(id) {
        const res = await likesApi.delete(id);
        return res.data;
    }
};
