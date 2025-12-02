package com.victoryspace.vics.category.query.service;

import com.victoryspace.vics.category.query.dto.CategoryQueryDTO;

import java.util.List;

public interface CategoryQueryService {

    List<CategoryQueryDTO> findAll();

}
