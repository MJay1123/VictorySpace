package com.victoryspace.vics.likes.command.application.service;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;

public interface LikesCommandService {
    LikesCommandDTO createLike(LikesCommandDTO dto);

    LikesCommandDTO deleteLike(int id);
}
