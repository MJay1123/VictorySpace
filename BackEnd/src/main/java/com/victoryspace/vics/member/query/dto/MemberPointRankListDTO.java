package com.victoryspace.vics.member.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPointRankListDTO {
    private Integer id;
    private String gender;
    private String nickname;
    private Integer gradeId;
    private String gradeName;
    private String gradeSymbol;
    private String profile;
    private Integer point;
}
