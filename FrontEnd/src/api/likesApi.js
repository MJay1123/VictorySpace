import http from './http';

const BASE_URL = '/likes';

export const likesApi = {

    /** ---------------------- Query ---------------------- **/

    // 전체 조회
    findAll: () => http.get(`${BASE_URL}`),

    // ID로 조회
    findById: (id) => http.get(`${BASE_URL}/${id}`),

    // voteId로 조회
    findByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),

    // memberId로 조회
    findByMemberId: (memberId) => http.get(`${BASE_URL}/member/${memberId}`),


    /** ---------------------- Command ---------------------- **/

    // 좋아요 생성
    createLike: (likesDTO) => http.post(`${BASE_URL}`, likesDTO),

    // 좋아요 삭제
    deleteLike: (id) => http.delete(`${BASE_URL}/${id}`)
};