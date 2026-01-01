package com.victoryspace.vics.member.command.application.service;

import com.victoryspace.vics.common.error.BusinessException;
import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.member.command.application.dto.request.MemberUpdateRequestDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberDeleteResponseDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberUpdateReponseDTO;
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
    public MemberUpdateReponseDTO updateMember(Integer id, MemberUpdateRequestDTO requestDTO) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        entity.update(
                requestDTO.getNickname(),
                requestDTO.getPassword(),
                requestDTO.getProfile()
        );
        memberRepository.save(entity);
        return memberCommandMapper.toUpdateReponseDTO(entity);
    }

    @Override
    public MemberDeleteResponseDTO deleteMember(Integer id) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        entity.delete();
        memberRepository.save(entity);
        return memberCommandMapper.toDeleteResponseDTO(entity);
    }
}
