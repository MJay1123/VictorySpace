package com.victoryspace.vics.comment.query.controller;

import com.victoryspace.vics.comment.query.dto.CommentQueryDTO;
import com.victoryspace.vics.comment.query.service.CommentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentQueryController {
    private final CommentQueryService queryService;

    @Autowired
    public CommentQueryController(CommentQueryService queryService){
        this.queryService = queryService;
    }
    @GetMapping
    public List<CommentQueryDTO> findAll(){
        List<CommentQueryDTO> dtoList = queryService.findAll();
        return dtoList;
    }
    @GetMapping("/{id}")
    public CommentQueryDTO findById(@PathVariable("id") int id){
        CommentQueryDTO dto = queryService.findById(id);
        return dto;
    }
    @GetMapping("/vote/{voteId}")
    public List<CommentQueryDTO> findByVoteId(@PathVariable("voteId") int voteId){
        List<CommentQueryDTO> dtoList = queryService.findByVoteId(voteId);
        return dtoList;
    }
    @GetMapping("/member/{memberId}")
    public List<CommentQueryDTO> findByMemberId(@PathVariable("memberId") int memberId){
        List<CommentQueryDTO> dtoList = queryService.findByMemberId(memberId);
        return dtoList;
    }
}
