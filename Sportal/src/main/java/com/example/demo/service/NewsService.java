package com.example.demo.service;

import com.example.demo.dto.AddNewsRequestDTO;
import com.example.demo.dto.AddNewsResponseDTO;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;




    public AddNewsResponseDTO addNews(AddNewsRequestDTO requestDTO) {
        News news = new News(requestDTO);
        news.setCreatedAt(LocalDateTime.now());
        news = newsRepository.save(news);

        return new AddNewsResponseDTO(news);
    }
}
