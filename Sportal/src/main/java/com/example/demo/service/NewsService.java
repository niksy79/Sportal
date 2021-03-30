package com.example.demo.service;

import com.example.demo.dto.newsdto.AddNewsRequestDTO;
import com.example.demo.dto.newsdto.AddNewsResponseDTO;
import com.example.demo.dto.newsdto.NewsByNameAndCategoryRequestDTO;
import com.example.demo.dto.newsdto.NewsByTitleAndCategoryResponseDTO;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public NewsByTitleAndCategoryResponseDTO byTitleAndCategory(NewsByNameAndCategoryRequestDTO requestDTO){
        News news = newsRepository.findByTitleAndCategory_Name(requestDTO.getTitle(), requestDTO.getCategoryName());
        news.setOwner(userRepository.findById(news.getOwner().getId()).get());

        return new NewsByTitleAndCategoryResponseDTO(news);
    }





}
