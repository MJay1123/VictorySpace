package com.victoryspace.vics.comment.query.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteCommentDTO {
    private Integer id;
    private Integer memberId;
    private String memberNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
