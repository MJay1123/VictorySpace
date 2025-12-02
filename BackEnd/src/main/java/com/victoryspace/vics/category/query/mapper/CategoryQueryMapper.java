package com.victoryspace.vics.category.query.mapper;

import com.victoryspace.vics.category.query.dto.CategoryQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryQueryMapper {
    List<CategoryQueryDTO> findAll();
}
