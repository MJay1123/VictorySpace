// src/api/voteApi.js
import http from "./http";

const BASE_URL = "/vote";

export const voteApi = {
  /** --- Query --- */
  getAllVotes: () => http.get(`${BASE_URL}`),

  getVoteById: (id) => http.get(`${BASE_URL}/${id}`),

  searchVotes: (searchDto) => http.post(`${BASE_URL}/search`, searchDto),

  getVotesByMemberId: (memberId) =>
    http.get(`${BASE_URL}/member/${memberId}`),

  getVotesByChallengerId: (challengerId) =>
    http.get(`${BASE_URL}/challenger/${challengerId}`),

  /** --- Command --- */
  createVote: (voteDto) => http.post(`${BASE_URL}`, voteDto),

  updateVote: (id, voteDto) => http.patch(`${BASE_URL}/${id}`, voteDto),

  challengeVote: (id, voteDto) =>
    http.patch(`${BASE_URL}/challenge/${id}`, voteDto),

  deleteVote: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default voteApi;
