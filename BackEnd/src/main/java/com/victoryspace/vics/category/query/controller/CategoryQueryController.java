package com.victoryspace.vics.category.query.controller;

import com.victoryspace.vics.category.query.dto.CategoryQueryDTO;
import com.victoryspace.vics.category.query.service.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryQueryController {
    private final CategoryQueryService queryService;

    @Autowired
    public CategoryQueryController(CategoryQueryService queryService){
        this.queryService = queryService;
    }
    @GetMapping
    public List<CategoryQueryDTO> findAll(){
        List<CategoryQueryDTO> dtoList = queryService.findAll();
        return dtoList;
    }
}
