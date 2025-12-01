// src/api/voterApi.js
import api from "./api";

const BASE_URL = "/voter";

export const voterApi = {
  /** --- Query --- */
  getAllVoters: () => api.get(`${BASE_URL}`),

  getVoterById: (id) => api.get(`${BASE_URL}/${id}`),

  getVotersByVoteId: (voteId) => api.get(`${BASE_URL}/vote/${voteId}`),

  getVotersByMemberId: (memberId) => api.get(`${BASE_URL}/member/${memberId}`),

  /** --- Command --- */
  createVoter: (voterDto) => api.post(`${BASE_URL}`, voterDto),

  deleteVoter: (id) => api.delete(`${BASE_URL}/${id}`),
};

export default voterApi;
