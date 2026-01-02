package com.victoryspace.vics.likes.query.controller;

import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;
import com.victoryspace.vics.likes.query.service.LikesQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikesQueryController {
    private final LikesQueryService queryService;

    @Autowired
    public LikesQueryController(LikesQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("vote/{voteId}")
    public List<LikesListDTO> findByVoteId(@PathVariable("voteId") int voteId){
        return queryService.findByVoteId(voteId);
    }
}
