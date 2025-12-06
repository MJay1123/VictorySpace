package com.victoryspace.vics.member.command.application.service;

import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;
import com.victoryspace.vics.member.command.application.mapper.MemberCommandMapper;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import com.victoryspace.vics.member.command.domain.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final MemberCommandMapper memberCommandMapper;

    @Override
    public MemberCommandDTO createMember(MemberCommandDTO dto) {
        MemberEntity entity = new MemberEntity();
        entity.setName(dto.getName());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setNickname(dto.getNickname());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setGradeId(dto.getGradeId());
        entity.setProfile(dto.getProfile());
        entity.setPoint(1000);
        entity.setRole("ROLE_USER");
        MemberEntity createdMemberEntity = memberRepository.save(entity);
        return memberCommandMapper.toDto(createdMemberEntity);
    }

    @Override
    public MemberCommandDTO updateMember(Integer id, MemberCommandDTO dto) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        entity.setName(dto.getName());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setNickname(dto.getNickname());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setGradeId(dto.getGradeId());
        entity.setProfile(dto.getProfile());
        entity.setPoint(dto.getPoint());
        MemberEntity updatedMemberEntity = memberRepository.save(entity);
        return memberCommandMapper.toDto(updatedMemberEntity);
    }

    @Override
    public MemberCommandDTO deleteMember(Integer id) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setDeletedAt(LocalDateTime.now());
        MemberEntity deletedMemberEntity = memberRepository.save(entity);
        return memberCommandMapper.toDto(deletedMemberEntity);
    }
}
