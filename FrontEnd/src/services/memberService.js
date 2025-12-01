// src/services/memberService.js
import { memberApi } from '@/api/memberApi';

export const memberService = {

    /** ---------- Query ---------- **/
    async fetchAll() {
        const res = await memberApi.getAll();
        return res.data;
    },

    async fetchById(id) {
        const res = await memberApi.getById(id);
        return res.data;
    },

    async fetchByNickname(nickname) {
        const res = await memberApi.findByNickname(nickname);
        return res.data;
    },

    async fetchOrdered(orderBy = 'id', direction = 'asc') {
        const res = await memberApi.order(orderBy, direction);
        return res.data;
    },


    /** ---------- Command ---------- **/
    async createMember(data) {
        const res = await memberApi.create(data);
        return res.data;
    },

    async updateMember(id, data) {
        const res = await memberApi.update(id, data);
        return res.data;
    },

    async deleteMember(id) {
        const res = await memberApi.delete(id);
        return res.data;
    },

    async login(email, password) {
        const res = await memberApi.login({ email, password });

        // 로그인 성공 시 localStorage에 저장
        if (res.data) {
            const { password: _, ...safeData } = res.data;
            localStorage.setItem("userInfo", JSON.stringify(safeData));
        }

        return res.data;
    }
};
