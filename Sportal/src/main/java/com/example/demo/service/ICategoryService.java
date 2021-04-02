package com.example.demo.service;

import com.example.demo.model.Category;

public interface ICategoryService {

    Category getCategoryById(int id);

    void save(Category c);



}
