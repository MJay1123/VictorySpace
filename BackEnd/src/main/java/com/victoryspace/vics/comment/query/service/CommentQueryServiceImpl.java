package com.victoryspace.vics.comment.query.service;

import com.victoryspace.vics.auth.SecurityUtil;
import com.victoryspace.vics.comment.query.dto.CommentQueryDTO;
import com.victoryspace.vics.comment.query.dto.response.VoteCommentDTO;
import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;
import com.victoryspace.vics.comment.query.mapper.CommentQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {
    private final CommentQueryMapper mapper;

    @Override
    public List<CommentQueryDTO> findAll(){
        List<CommentQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public CommentQueryDTO findById(int id) {
        CommentQueryDTO dto = mapper.findById(id);
        return dto;
    }

    @Override
    public List<VoteCommentResponseDTO> findByVoteId(int voteId) {
        Integer memberId = SecurityUtil.getCurrentMemberId();
        List<VoteCommentDTO> dtoList = mapper.findByVoteId(voteId);
        return dtoList.stream()
                .map(c -> VoteCommentResponseDTO.builder()
                        .id(c.getId())
                        .memberNickname(c.getMemberNickname())
                        .content(c.getContent())
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .canEdit(c.getMemberId().equals(memberId))
                        .build()
                ).toList();
    }

    @Override
    public List<CommentQueryDTO> findByMemberId(int memberId) {
        List<CommentQueryDTO> dtoList = mapper.findByMemberId(memberId);
        return dtoList;
    }
}
