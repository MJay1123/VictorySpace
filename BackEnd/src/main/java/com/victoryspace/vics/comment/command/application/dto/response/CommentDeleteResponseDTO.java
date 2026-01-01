package com.victoryspace.vics.comment.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDeleteResponseDTO {
    private Integer id;
    private LocalDateTime deletedAt;
}
