package com.victoryspace.vics.member.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDTO {
    private Integer id;
    private String nickname;
    private String password;
    private String profile;
}
