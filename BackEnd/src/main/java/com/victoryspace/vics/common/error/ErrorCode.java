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
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH-003", "존재하지 않는 이메일입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH-004", "이메일이 이미 존재합니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH-005", "닉네임이 이미 존재합니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원 정보가 존재하지 않습니다."),
    DELETED_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER-002", "이미 삭제된 회원입니다."),

    // Vote
    VOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "VOTE-001",  "존재하지 않는 투표입니다."),
    VOTE_ALREADY_CHALLENGED(HttpStatus.BAD_REQUEST, "V0TE-002",  "이미 경쟁자가 할당된 투표입니다."),
    VOTE_SELF_CHALLENGED(HttpStatus.BAD_REQUEST, "VOTE-003", "자신의 투표에 도전할 수 없습니다."),

    // Voter
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "VOTER-001",  "이미 투표에 참여했습니다."),
    VOTER_NOT_FOUND(HttpStatus.NOT_FOUND, "VOTER-002", "존재하지 않는 투표자입니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT-001", "존재하지 않는 댓글입니다."),

    // Likes
    LIKES_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "LIKES-001", "이미 좋아요가 존재합니다."),
    LIKES_NOT_FOUMD(HttpStatus.NOT_FOUND, "LIKES-002", "좋아요가 존재하지 않습니다.")
    // Bookmark
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
