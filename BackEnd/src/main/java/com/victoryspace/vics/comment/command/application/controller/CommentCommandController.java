package com.victoryspace.vics.comment.command.application.controller;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentCreateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentUpdateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentCreateResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentDeleteResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentUpdateResponseDTO;
import com.victoryspace.vics.comment.command.application.service.CommentCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentCommandController {
    private final CommentCommandService commandService;

    @Autowired
    public CommentCommandController(CommentCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public CommentCreateResponseDTO createComment(@RequestBody CommentCreateRequestDTO requestDTO) {
        return commandService.createComment(requestDTO);
    }

    @PatchMapping("/{id}")
    public CommentUpdateResponseDTO updateComment(@PathVariable Integer id, @RequestBody CommentUpdateRequestDTO requestDTO) {
        return commandService.updateComment(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public CommentDeleteResponseDTO deleteComment(@PathVariable Integer id) {
        return commandService.deleteComment(id);
    }
}
