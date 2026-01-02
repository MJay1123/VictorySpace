import http from './http';

const BASE_URL = '/likes';

export const likesApi = {

    /** ---------------------- Query ---------------------- **/

    // voteId로 조회
    findByVoteId: (voteId) => http.get(`${BASE_URL}/vote/${voteId}`),


    /** ---------------------- Command ---------------------- **/

    // 좋아요 생성
    createLike: (likesDTO) => http.post(`${BASE_URL}`, likesDTO),

    // 좋아요 삭제
    deleteLike: (id) => http.delete(`${BASE_URL}/${id}`)
};

export default likesApi