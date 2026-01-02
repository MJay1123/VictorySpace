package com.victoryspace.vics.vote.query.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteQueryDetailResponseDTO {
    private Integer id;
    private String title;

    private Integer categoryId;
    private String categoryName;

    private Integer memberId;
    private String memberNickname;
    private String content;

    private Integer challengerId;
    private String challengerNickname;
    private String challengerContent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String duration;
    private LocalDateTime endedAt;

    private Integer likesCount;
    private Integer commentsCount;

    private Integer homeCount;
    private Integer awayCount;
    private Integer neutralCount;
}
