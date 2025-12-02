package com.victoryspace.vics.category.query.service;

import com.victoryspace.vics.category.query.dto.CategoryQueryDTO;
import com.victoryspace.vics.category.query.mapper.CategoryQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryQueryMapper mapper;

    @Override
    public List<CategoryQueryDTO> findAll(){
        List<CategoryQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }
}
