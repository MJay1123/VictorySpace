package com.victoryspace.vics.vote.query.controller;

import com.victoryspace.vics.vote.query.dto.response.VoteQueryDetailResponseDTO;
import com.victoryspace.vics.vote.query.dto.VoteQueryDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryListResponseDTO;
import com.victoryspace.vics.vote.query.dto.request.VoteSearchDTO;
import com.victoryspace.vics.vote.query.service.VoteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
public class VoteQueryController {
    private final VoteQueryService voteQueryService;

    @Autowired
    public VoteQueryController(VoteQueryService voteQueryService){
        this.voteQueryService = voteQueryService;
    }
    @GetMapping
    public List<VoteQueryListResponseDTO> findAll(){
        List<VoteQueryListResponseDTO> list = voteQueryService.findAll();
        return list;
    }
    @GetMapping("/{id}")
    public VoteQueryDetailResponseDTO findById(@PathVariable("id") int id){
        VoteQueryDetailResponseDTO dto = voteQueryService.findById(id);
        return dto;
    }
    @PostMapping("/search")
    public List<VoteQueryListResponseDTO> search(@RequestBody VoteSearchDTO voteSearchDTO){
        List<VoteQueryListResponseDTO> dtoList = voteQueryService.search(voteSearchDTO);
        return dtoList;
    }

    @GetMapping("/member/{memberId}")
    public List<VoteQueryListResponseDTO> findByMemberId(@PathVariable("memberId") int memberId){
        List<VoteQueryListResponseDTO> dtoList = voteQueryService.findByMemberId(memberId);
        return dtoList;
    }

    @GetMapping("/challenger/{challengerId}")
    public List<VoteQueryListResponseDTO> findByChallengerId(@PathVariable("challengerId") int challengerId){
        List<VoteQueryListResponseDTO> dtoList = voteQueryService.findByChallengerId(challengerId);
        return dtoList;
    }
}
