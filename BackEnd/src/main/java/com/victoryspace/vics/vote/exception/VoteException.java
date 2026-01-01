package com.victoryspace.vics.vote.exception;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;

public class VoteException extends BusinessException {
    public VoteException(ErrorCode errorCode) {
        super(errorCode);
    }
}
