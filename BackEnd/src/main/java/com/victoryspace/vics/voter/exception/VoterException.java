package com.victoryspace.vics.voter.exception;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;

public class VoterException extends BusinessException {
    public VoterException(ErrorCode errorCode) {
        super(errorCode);
    }
}
