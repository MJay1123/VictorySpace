package com.victoryspace.vics.comment.exception;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;

public class CommentException extends BusinessException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
