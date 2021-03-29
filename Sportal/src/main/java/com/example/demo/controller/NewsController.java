package com.example.demo.controller;

import com.example.demo.dto.AddNewsRequestDTO;
import com.example.demo.dto.AddNewsResponseDTO;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        News news = new News(requestDTO);
        User u = sessionManager.getLoggedUser(ses);
        news.setCategory(categoryRepository.findByName(name));
        news.setCreatedAt(LocalDateTime.now());
        news.setOwner(u);
        newsRepository.save(news);
        return new AddNewsResponseDTO(news);



    }




}
