package com.victoryspace.vics.likes.command.application.controller;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.application.service.LikesCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikesCommandController {
    private final LikesCommandService commandService;

    @Autowired
    public LikesCommandController(LikesCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public LikesCommandDTO createLike(@RequestBody LikesCommandDTO dto){
        LikesCommandDTO createdDto = commandService.createLike(dto);
        return createdDto;
    }

    @DeleteMapping("/{id}")
    public LikesCommandDTO deleteLike(@PathVariable int id){
        LikesCommandDTO deletedDto = commandService.deleteLike(id);
        return deletedDto;
    }

}
