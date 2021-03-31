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
import java.util.List;

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


    @PutMapping("/news/add")
    public AddNewsResponseDTO add(@RequestBody AddNewsRequestDTO requestDTO, HttpSession ses){
        User u = sessionManager.getLoggedUser(ses);
        if (!u.getIsAdmin()){
            throw new AuthenticationException("you do not have permission to write news");
        }
        return newsService.addNews(requestDTO, u);
    }

    @GetMapping("/news")
    public List<NewsByTitleResponseDTO> getByName(@RequestBody NewsByTitleRequestDTO requestDTO){
        return newsService.findByName(requestDTO);

    }
    @GetMapping("/news/{name}")
    public NewsByCategoryDTO getByCategory(@PathVariable String name){
       Category c = categoryRepository.findByNameContaining(name);
       if (c == null){
           throw new NotFoundException("Category not found");
       }
       return new NewsByCategoryDTO(c);
    }

    @GetMapping("/news/sort")
    public List<AllNewsWithViewsDTO> sortedByViews(){
        return newsService.viewsDTO();
    }

    @GetMapping("/news/filter")
    public List<NewsByCategoryAndTitleDTO> byNameAndCategory(@RequestBody NewsSearchRequestDTO requestDTO){
        return newsService.filter(requestDTO);
    }
    @GetMapping("/news/read/news")
    public ReadNewsDTO read(){
        return newsService.readRandomNews();
    }

}
