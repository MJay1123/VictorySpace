package com.victoryspace.vics.likes.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesQueryDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private LocalDateTime createdAt;
}
