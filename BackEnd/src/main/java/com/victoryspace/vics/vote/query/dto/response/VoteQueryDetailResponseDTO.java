package com.victoryspace.vics.vote.query.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteQueryDetailResponseDTO {
    private Integer id;
    private String title;
    private String categoryName;

    private String memberNickname;
    private String content;

    private String challengerNickname;
    private String challengerContent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String duration;
    private LocalDateTime endedAt;

    private Integer homeCount;
    private Integer awayCount;
    private Integer neutralCount;
}
