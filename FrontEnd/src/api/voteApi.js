import http from "./http";

const BASE_URL = "/vote";

export const voteApi = {

  /** ---------------------- Query ---------------------- **/

  findAll: () => http.get(`${BASE_URL}`),

  findById: (id) => http.get(`${BASE_URL}/${id}`),

  search: (searchDto) => http.post(`${BASE_URL}/search`, searchDto),

  findByMemberId: (memberId) =>
    http.get(`${BASE_URL}/member/${memberId}`),

  findByChallengerId: (challengerId) =>
    http.get(`${BASE_URL}/challenger/${challengerId}`),


  /** ---------------------- Command ---------------------- **/

  createVote: (voteDto) => http.post(`${BASE_URL}`, voteDto),

  updateVote: (id, voteDto) => http.patch(`${BASE_URL}/${id}`, voteDto),

  challengeVote: (id, voteDto) =>
    http.patch(`${BASE_URL}/challenge/${id}`, voteDto),

  deleteVote: (id) => http.delete(`${BASE_URL}/${id}`),
};

export default voteApi;
