package com.victoryspace.vics.member.exception;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;

public class MemberException extends BusinessException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
