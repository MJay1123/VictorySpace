package com.victoryspace.vics.comment.command.application.service;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;

public interface CommentCommandService {
    CommentCommandDTO createComment(CommentCommandDTO dto);

    CommentCommandDTO updateComment(Integer id, CommentCommandDTO dto);

    CommentCommandDTO deleteComment(Integer id);
}
