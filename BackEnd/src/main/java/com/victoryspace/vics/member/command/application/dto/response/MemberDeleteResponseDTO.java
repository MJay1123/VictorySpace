package com.victoryspace.vics.member.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberDeleteResponseDTO {
    private LocalDateTime deletedAt;
}
