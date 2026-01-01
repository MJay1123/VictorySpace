/**
 * BusinessException 에러 처리 유틸리티
 */

/**
 * 에러 객체에서 BusinessException 정보를 추출
 * @param {Error} error - axios 에러 객체
 * @returns {Object|null} BusinessException 정보 또는 null
 */
export const extractBusinessError = (error) => {
    if (error?.businessError) {
        return error.businessError;
    }
    
    if (error?.response?.data) {
        const data = error.response.data;
        if (data.success === false && data.name && data.code) {
            return {
                success: data.success,
                name: data.name,
                code: data.code,
                message: data.message,
                timestamp: data.timestamp
            };
        }
    }
    
    return null;
};

/**
 * BusinessException을 console.log로 출력
 * @param {Object} businessError - BusinessException 객체
 */
export const logBusinessError = (businessError) => {
    if (!businessError) return;
    
    console.log('🚨 BusinessException 발생:', {
        '에러 이름': businessError.name,
        '에러 코드': businessError.code,
        '에러 메시지': businessError.message,
        '발생 시간': businessError.timestamp,
        '전체 에러 객체': businessError
    });
};

/**
 * 에러에서 사용자 친화적인 메시지 추출
 * @param {Error} error - 에러 객체
 * @returns {string} 사용자에게 보여줄 메시지
 */
export const getErrorMessage = (error) => {
    const businessError = extractBusinessError(error);
    
    if (businessError) {
        return businessError.message;
    }
    
    if (error?.response?.data?.message) {
        return error.response.data.message;
    }
    
    if (error?.message) {
        return error.message;
    }
    
    return '알 수 없는 오류가 발생했습니다.';
};

/**
 * 에러 처리 헬퍼 함수
 * @param {Error} error - 에러 객체
 * @param {Object} options - 옵션
 * @param {boolean} options.log - console.log 출력 여부 (기본: true)
 * @param {boolean} options.throw - 에러를 다시 throw할지 여부 (기본: false)
 * @returns {Object|null} BusinessException 정보 또는 null
 */
export const handleError = (error, options = {}) => {
    const { log = true, throw: shouldThrow = false } = options;
    
    const businessError = extractBusinessError(error);
    
    if (businessError && log) {
        logBusinessError(businessError);
    }
    
    if (shouldThrow) {
        throw error;
    }
    
    return businessError;
};

