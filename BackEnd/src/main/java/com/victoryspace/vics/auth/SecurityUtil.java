package com.victoryspace.vics.auth;

import com.victoryspace.vics.auth.dto.CustomUserDetails;
import com.victoryspace.vics.auth.exception.AuthException;
import com.victoryspace.vics.common.error.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        // 인스턴스화 방지
    }

    public static Integer getCurrentMemberId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException(ErrorCode.AUTHORIZATION_NOT_FOUND);
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new AuthException(ErrorCode.WRONG_AUTHORIZATION);
        }

        return userDetails.getMemberId();
    }
}
