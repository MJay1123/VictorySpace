package com.victoryspace.vics.comment.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentCreateResponseDTO {

    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private String content;
    private LocalDateTime createdAt;
}
