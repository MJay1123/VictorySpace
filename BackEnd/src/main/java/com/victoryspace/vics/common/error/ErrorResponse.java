package com.victoryspace.vics.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {

    private boolean success;
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                false,
                errorCode.name(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );
    }
}
