package com.victoryspace.vics.member.command.application.service;

import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;
import com.victoryspace.vics.member.command.application.dto.request.MemberUpdateRequestDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberDeleteResponseDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberUpdateResponseDTO;
import com.victoryspace.vics.member.command.application.mapper.MemberCommandMapper;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import com.victoryspace.vics.member.command.domain.repository.MemberRepository;
import com.victoryspace.vics.member.exception.MemberException;
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
    public MemberUpdateResponseDTO updateMember(Integer id, MemberUpdateRequestDTO dto) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        if(entity.getDeletedAt() != null){
            throw new MemberException(ErrorCode.DELETED_MEMBER);
        }
        entity.update(
                dto.getNickname(),
                dto.getPassword(),
                dto.getProfile()
        );
        memberRepository.save(entity);
        return memberCommandMapper.toUpdateResponseDTO(entity);
    }

    @Override
    public MemberDeleteResponseDTO deleteMember(Integer id) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        if(entity.getDeletedAt() != null){
            throw new MemberException(ErrorCode.DELETED_MEMBER);
        }
        entity.delete();
        memberRepository.save(entity);
        return memberCommandMapper.toDeleteResponseDTO(entity);
    }
}
