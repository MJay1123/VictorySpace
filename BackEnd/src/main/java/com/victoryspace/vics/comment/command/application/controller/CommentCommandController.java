package com.victoryspace.vics.comment.command.application.controller;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
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
    public CommentCommandDTO createComment(@RequestBody CommentCommandDTO dto) {
        CommentCommandDTO createdDto = commandService.createComment(dto);
        return createdDto;
    }

    @PatchMapping("/{id}")
    public CommentCommandDTO updateComment(@PathVariable Integer id, @RequestBody CommentCommandDTO dto) {
        CommentCommandDTO updatedDto = commandService.updateComment(id, dto);
        return updatedDto;
    }

    @DeleteMapping("/{id}")
    public CommentCommandDTO deleteComment(@PathVariable Integer id) {
        CommentCommandDTO deletedDto = commandService.deleteComment(id);
        return deletedDto;
    }
}
