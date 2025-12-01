// src/service/voteService.js
import voteApi from "@/api/voteApi";

export const voteService = {
  async fetchAllVotes() {
    const { data } = await voteApi.getAllVotes();
    return data;
  },

  async fetchVote(id) {
    const { data } = await voteApi.getVoteById(id);
    return data;
  },

  async search(searchDto) {
    const { data } = await voteApi.searchVotes(searchDto);
    return data;
  },

  async fetchByMemberId(memberId) {
    const { data } = await voteApi.getVotesByMemberId(memberId);
    return data;
  },

  async fetchByChallengerId(challengerId) {
    const { data } = await voteApi.getVotesByChallengerId(challengerId);
    return data;
  },

  async create(voteDto) {
    const { data } = await voteApi.createVote(voteDto);
    return data;
  },

  async update(id, voteDto) {
    const { data } = await voteApi.updateVote(id, voteDto);
    return data;
  },

  async challenge(id, voteDto) {
    const { data } = await voteApi.challengeVote(id, voteDto);
    return data;
  },

  async delete(id) {
    const { data } = await voteApi.deleteVote(id);
    return data;
  },
};

export default voteService;
 