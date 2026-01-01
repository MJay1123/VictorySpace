package com.victoryspace.vics.voter.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "voter")
public class VoterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vote_id")
    private Integer voteId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "content")
    private String content;

    public static VoterEntity create(Integer voteId, Integer mdmeberId, String content) {
        VoterEntity voterEntity = new VoterEntity();
        voterEntity.voteId = voteId;
        voterEntity.memberId = mdmeberId;
        voterEntity.content = content;
        return voterEntity;
    }
}
