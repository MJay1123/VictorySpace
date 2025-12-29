package com.victoryspace.vics.vote.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
