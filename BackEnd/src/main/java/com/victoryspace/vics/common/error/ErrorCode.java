package com.victoryspace.vics.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "서버 오류가 발생했습니다."),

    // Auth
    AUTHORIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH-001",  "인증 정보가 없습니다."),
    WRONG_AUTHORIZATION(HttpStatus.BAD_REQUEST, "AUTH-002", "잘못된 인증 주체입니다."),

    // Vote
    VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "VOTE-001",  "존재하지 않는 투표입니다."),
    VOTE_ALREADY_CHALLENGED(HttpStatus.BAD_REQUEST, "V0TE-002",  "이미 경쟁자가 할당된 투표입니다."),
    VOTE_SELF_CHALLENGED(HttpStatus.BAD_REQUEST, "VOTE-003", "자신의 투표에 도전할 수 없습니다."),

    // Voter
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "VOTER-001",  "이미 투표에 참여했습니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT-001", "존재하지 않는 댓글입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
