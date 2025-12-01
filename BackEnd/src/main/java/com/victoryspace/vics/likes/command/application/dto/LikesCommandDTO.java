package com.victoryspace.vics.likes.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesCommandDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private LocalDateTime createdAt;
}
