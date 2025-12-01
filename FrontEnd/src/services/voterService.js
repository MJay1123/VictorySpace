// src/service/voterService.js
import voterApi from "@/api/voterApi";

export const voterService = {
  
  async fetchAll() {
    const { data } = await voterApi.getAllVoters();
    return data;
  },

  async fetchById(id) {
    const { data } = await voterApi.getVoterById(id);
    return data;
  },

  async fetchByVoteId(voteId) {
    const { data } = await voterApi.getVotersByVoteId(voteId);
    return data;
  },

  async fetchByMemberId(memberId) {
    const { data } = await voterApi.getVotersByMemberId(memberId);
    return data;
  },

  async create(voterDto) {
    const { data } = await voterApi.createVoter(voterDto);
    return data;
  },

  async delete(id) {
    const { data } = await voterApi.deleteVoter(id);
    return data;
  },
};

export default voterService;
