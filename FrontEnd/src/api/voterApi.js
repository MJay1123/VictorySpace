// src/api/voterApi.js
import http from "./http";

const BASE_URL = "/voter";

export const voterApi = {
  /** --- Query --- */
  getAllVoters: () => http.get(`${BASE_URL}`),

  getVoterById: (id) => http.get(`${BASE_URL}/${id}`),

  getVotersByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),

  getVotersByMemberId: (memberId) => http.get(`${BASE_URL}/member/${memberId}`),

  /** --- Command --- */
  createVoter: (voterDto) => http.post(`${BASE_URL}`, voterDto),

  deleteVoter: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default voterApi;
