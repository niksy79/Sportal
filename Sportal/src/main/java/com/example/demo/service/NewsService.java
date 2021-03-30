package com.example.demo.service;

import com.example.demo.dto.newsdto.NewsByTitleRequestDTO;
import com.example.demo.dto.newsdto.NewsByTitleResponseDTO;
import com.example.demo.model.News;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public NewsByTitleResponseDTO byTitleAndCategory(NewsByTitleRequestDTO requestDTO){

        News oNews = newsRepository.findFirstByTitleIsContaining(requestDTO.getTitle());

      return new NewsByTitleResponseDTO(oNews);
    }





}
