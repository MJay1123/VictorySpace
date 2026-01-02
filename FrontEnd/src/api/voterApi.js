// src/api/voterApi.js
import http from "./http";

const BASE_URL = "/voter";

export const voterApi = {
  /** --- Query --- */

  findByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),

  /** --- Command --- */
  createVoter: (voterDTO) => http.post(`${BASE_URL}`, voterDTO),

  deleteVoter: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default voterApi;
