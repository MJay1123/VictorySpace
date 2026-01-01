package com.victoryspace.vics.likes.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "likes")
public class LikesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vote_id")
    private Integer voteId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static LikesEntity create(Integer voteId, Integer memberId) {
        LikesEntity entity = new LikesEntity();
        entity.voteId = voteId;
        entity.memberId = memberId;
        entity.createdAt = LocalDateTime.now();
        return entity;
    }
}
