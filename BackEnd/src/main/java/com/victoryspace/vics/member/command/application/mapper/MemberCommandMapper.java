package com.victoryspace.vics.member.command.application.mapper;

import com.victoryspace.vics.auth.dto.response.RegisterResponseDTO;
import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberCommandMapper {
    public MemberCommandDTO toDto(MemberEntity entity) {
        MemberCommandDTO dto = new MemberCommandDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGender(entity.getGender());
        dto.setBirthday(entity.getBirthday());
        dto.setNickname(entity.getNickname());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setGradeId(entity.getGradeId());
        dto.setProfile(entity.getProfile());
        dto.setPoint(entity.getPoint());
        dto.setRole(entity.getRole());
        return dto;
    }

    public RegisterResponseDTO toRegisterResponseDTO(MemberEntity entity) {
        return RegisterResponseDTO.builder()
                .name(entity.getName())
                .gender(entity.getGender())
                .birthday(entity.getBirthday())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
