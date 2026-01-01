package com.victoryspace.vics.comment.command.application.service;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentCreateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentUpdateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentCreateResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentDeleteResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentUpdateResponseDTO;

public interface CommentCommandService {
    CommentCreateResponseDTO createComment(CommentCreateRequestDTO requestDTO);

    CommentUpdateResponseDTO updateComment(Integer id, CommentUpdateRequestDTO requestDTO);

    CommentDeleteResponseDTO deleteComment(Integer id);
}
