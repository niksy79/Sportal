package com.example.demo.controller;

import com.example.demo.dto.newsdto.*;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
public class NewsController extends AbstractController{

    @Autowired
    NewsService newsService;

    @Autowired
    SessionManager sessionManager;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @PutMapping("/news/{name}")
    public AddNewsResponseDTO add(@RequestBody AddNewsRequestDTO requestDTO, @PathVariable String name, HttpSession ses){
        User u = sessionManager.getLoggedUser(ses);
        if (!u.getIsAdmin()){
            throw new AuthenticationException("you do not have permission to write news");
        }
        News news = new News(requestDTO);
        news.setCategory(categoryRepository.findByName(name));
        news.setCreatedAt(LocalDateTime.now());
        news.setOwner(u);
        newsRepository.save(news);
        return new AddNewsResponseDTO(news);
    }

    @GetMapping("/news")
    public NewsByTitleResponseDTO searchByNameAndTitle(@RequestBody NewsByTitleRequestDTO requestDTO){
        return newsService.byTitleAndCategory(requestDTO);

    }
    @GetMapping("/news/{name}")
    public NewsByCategoryDTO byCategory(@PathVariable String name){
       Category c = categoryRepository.findByName(name);
       if (c == null){
           throw new NotFoundException("Category not found");
       }
       return new NewsByCategoryDTO(c);

    }






}
