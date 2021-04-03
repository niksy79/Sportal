package com.example.demo.service;
import com.example.demo.dto.newsdto.*;
import com.example.demo.model.News;
import com.example.demo.model.User;
import java.util.List;

public interface INewsService {

    AddNewsResponseDTO editNews(AddNewsRequestDTO requestDTO);

    AddNewsResponseDTO addNews(AddNewsRequestDTO requestDTO, User user);

    List<NewsByCategoryAndTitleDTO> filter(NewsSearchRequestDTO requestDTO);

    List<AllNewsWithViewsDTO> topFiveNews();

    List<NewsByTitleResponseDTO> findByName(NewsByTitleRequestDTO requestDTO);

    ReadNewsDTO readRandomNews();

    News getByID(long id);

    List<ReadNewsDTO> latestNews();

}
