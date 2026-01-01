package com.victoryspace.vics.likes.command.application.controller;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.application.dto.request.LikesCreateRequestDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesCreateResponseDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesDeleteResponseDTO;
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
    public LikesCreateResponseDTO createLikes(@RequestBody LikesCreateRequestDTO requestDTO){
        return commandService.createLikes(requestDTO);
    }

    @DeleteMapping("/{id}")
    public LikesDeleteResponseDTO deleteLikes(@PathVariable int id){
        return commandService.deleteLikes(id);
    }

}
