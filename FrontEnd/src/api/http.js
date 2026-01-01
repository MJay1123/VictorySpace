// src/api/http.js
import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 5000,
});

http.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    const publicUrls = ["/auth/login", "/auth/register"];

    const isPublic = publicUrls.some(url => config.url?.startsWith(url));

    if (!isPublic && token) {
        config.headers.Authorization = token.startsWith("Bearer")
            ? token
            : `Bearer ${token}`;
    }

    return config;
});

// Response Interceptor - BusinessException 처리
http.interceptors.response.use(
    (response) => {
        // 성공 응답은 그대로 반환
        return response;
    },
    (error) => {
        // 에러 응답 처리
        if (error.response) {
            const { data, status } = error.response;
            
            // BusinessException 형식인지 확인
            if (data && typeof data === 'object' && 'success' in data && data.success === false) {
                const businessError = {
                    success: data.success,
                    name: data.name,
                    code: data.code,
                    message: data.message,
                    timestamp: data.timestamp
                };
                
                // console.log로 BusinessException 정보 출력
                console.log('🚨 BusinessException 발생:', {
                    '에러 이름': businessError.name,
                    '에러 코드': businessError.code,
                    '에러 메시지': businessError.message,
                    '발생 시간': businessError.timestamp,
                    '상태 코드': status,
                    '전체 에러 객체': businessError
                });
                
                // 에러 객체에 businessError 정보 추가 (컴포넌트에서 사용 가능)
                error.businessError = businessError;
            } else {
                // 일반 에러 처리
                console.error('❌ API 에러:', {
                    '상태 코드': status,
                    '에러 메시지': data?.message || error.message,
                    '전체 응답': data
                });
            }
        } else if (error.request) {
            // 요청은 보냈지만 응답을 받지 못한 경우
            console.error('❌ 네트워크 에러: 서버에 연결할 수 없습니다.', error.request);
        } else {
            // 요청 설정 중 에러 발생
            console.error('❌ 요청 설정 에러:', error.message);
        }
        
        // 에러를 다시 throw하여 컴포넌트에서 catch할 수 있도록 함
        return Promise.reject(error);
    }
);

export default http;