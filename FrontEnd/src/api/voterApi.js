// src/api/voterApi.js
import http from "./http";

const BASE_URL = "/voter";

export const voterApi = {
  /** --- Query --- */
  findAll: () => http.get(`${BASE_URL}`),

  findById: (id) => http.get(`${BASE_URL}/${id}`),

  findByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),

  findByMemberId: (memberId) => http.get(`${BASE_URL}/member/${memberId}`),

  findByVoteAndMemberId: (voteId, memberId) => http.get(`${BASE_URL}/vote/${voteId}/member/${memberId}`),

  /** --- Command --- */
  createVoter: (voterDTO) => http.post(`${BASE_URL}`, voterDTO),

  deleteVoter: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default voterApi;
