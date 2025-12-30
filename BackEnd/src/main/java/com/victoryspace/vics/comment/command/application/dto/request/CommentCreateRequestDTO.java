package com.victoryspace.vics.comment.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDTO {
    private Integer voteId;
    private Integer memberId;
    private String content;
}
