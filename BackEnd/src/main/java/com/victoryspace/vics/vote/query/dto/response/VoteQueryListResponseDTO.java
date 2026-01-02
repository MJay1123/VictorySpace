package com.victoryspace.vics.vote.query.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteQueryListResponseDTO {
    private Integer id;
    private String title;

    private Integer categoryId;
    private String categoryName;

    private Integer memberId;
    private String memberNickname;

    private Integer challengerId;
    private String challengerNickname;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String duration;
    private LocalDateTime endedAt;

    private Integer likesCount;
    private Integer commentsCount;
}
