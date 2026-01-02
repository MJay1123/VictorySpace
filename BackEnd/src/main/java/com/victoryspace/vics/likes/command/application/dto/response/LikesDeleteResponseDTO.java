package com.victoryspace.vics.likes.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LikesDeleteResponseDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private LocalDateTime deletedAt;
}
