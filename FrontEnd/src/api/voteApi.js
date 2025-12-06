// src/api/voteApi.js
import api from "@/api/http.js";

const BASE_URL = "/vote";

export const voteApi = {
  /** --- Query --- */
  getAllVotes: () => api.get(`${BASE_URL}`),

  getVoteById: (id) => api.get(`${BASE_URL}/${id}`),

  searchVotes: (searchDto) => api.post(`${BASE_URL}/search`, searchDto),

  getVotesByMemberId: (memberId) =>
    api.get(`${BASE_URL}/member/${memberId}`),

  getVotesByChallengerId: (challengerId) =>
    api.get(`${BASE_URL}/challenger/${challengerId}`),

  /** --- Command --- */
  createVote: (voteDto) => api.post(`${BASE_URL}`, voteDto),

  updateVote: (id, voteDto) => api.patch(`${BASE_URL}/${id}`, voteDto),

  challengeVote: (id, voteDto) =>
    api.patch(`${BASE_URL}/challenge/${id}`, voteDto),

  deleteVote: (id) => api.delete(`${BASE_URL}/${id}`),
};

export default voteApi;
