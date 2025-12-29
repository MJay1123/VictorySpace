package com.victoryspace.vics.vote.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "content")
    private String content;

    @Column(name = "challenger_id")
    private Integer challengerId;

    @Column(name = "challenger_content")
    private String challengerContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "duration")
    private String duration;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    public static VoteEntity create(String title, Integer categoryId, String content, Integer memberId, String duration) {
        VoteEntity vote = new VoteEntity();
        vote.title = title;
        vote.categoryId = categoryId;
        vote.content = content;
        vote.memberId = memberId;
        vote.duration = duration;
        vote.createdAt = LocalDateTime.now();
        return vote;
    }

    public void update(String title, Integer categoryId, String content, String duration) {
        this.title = title;
        this.categoryId = categoryId;
        this.content = content;
        this.duration = duration;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void challenge(Integer challengerId, String challengerContent) {
        this.challengerId = challengerId;
        this.challengerContent = challengerContent;
        this.endedAt = calculateEndedAt();
        this.updatedAt = LocalDateTime.now();
    }

    private LocalDateTime calculateEndedAt() {
        LocalDateTime now = LocalDateTime.now();
        if (duration.endsWith("h")) {
            return now.plusHours(Integer.parseInt(duration.replace("h", "")));
        }
        if (duration.endsWith("d")) {
            return now.plusDays(Integer.parseInt(duration.replace("d", "")));
        }
        throw new IllegalArgumentException("Invalid duration format: " + duration);
    }
}
