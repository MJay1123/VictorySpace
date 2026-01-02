package com.victoryspace.vics.comment.query.controller;

import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;
import com.victoryspace.vics.comment.query.service.CommentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentQueryController {
    private final CommentQueryService queryService;

    @Autowired
    public CommentQueryController(CommentQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/vote/{voteId}")
    public List<VoteCommentResponseDTO> findByVoteId(@PathVariable("voteId") int voteId){
        List<VoteCommentResponseDTO> dtoList = queryService.findByVoteId(voteId);
        return dtoList;
    }
}
