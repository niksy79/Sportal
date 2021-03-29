package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController extends AbstractController{

    @Autowired
    CategoryRepository categoryRepository;

    @PutMapping("/category")
    public Category addCategory(@RequestBody Category c){
        return categoryRepository.save(c);

        //TODO да направя нещата през сървиса

    }



}
