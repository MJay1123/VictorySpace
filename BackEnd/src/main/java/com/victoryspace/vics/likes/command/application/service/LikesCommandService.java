package com.victoryspace.vics.likes.command.application.service;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.application.dto.request.LikesCreateRequestDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesCreateResponseDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesDeleteResponseDTO;

public interface LikesCommandService {

    LikesCreateResponseDTO createLikes(LikesCreateRequestDTO requestDTO);

    LikesDeleteResponseDTO deleteLikes(int id);
}
