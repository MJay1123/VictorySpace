package com.victoryspace.vics.likes.exception;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;

public class LikesException extends BusinessException {
    public LikesException(ErrorCode errorCode) {
        super(errorCode);
    }
}
