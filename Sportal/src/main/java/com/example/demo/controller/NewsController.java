package com.example.demo.controller;

import com.example.demo.dto.newsdto.AddNewsRequestDTO;
import com.example.demo.dto.newsdto.AddNewsResponseDTO;
import com.example.demo.dto.newsdto.NewsByNameAndCategoryRequestDTO;
import com.example.demo.dto.newsdto.NewsByTitleAndCategoryResponseDTO;
import com.example.demo.exeptions.AuthenticationException;
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
    public NewsByTitleAndCategoryResponseDTO searchByNameAndTitle(@RequestBody NewsByNameAndCategoryRequestDTO requestDTO){

        return newsService.byTitleAndCategory(requestDTO);

    }






}
