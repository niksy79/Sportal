package com.example.demo.service;

import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategoryById(int id){
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new NotFoundException("Category not found");
        }
        return category.get();
    }

    public void save(Category c){
        categoryRepository.save(c);
    }

    public Category findByName(String name){
        Category category = categoryRepository.findByName(name);
        if (category == null){
            throw new NotFoundException("Category not found");
        }
        return category;
    }


}
