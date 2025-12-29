package com.victoryspace.vics.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // Vote
    VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 투표입니다."),

    // Voter
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "이미 투표에 참여했습니다.");

    private final HttpStatus status;
    private final String message;
}
