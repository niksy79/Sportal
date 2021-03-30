package com.example.demo.service;
import com.example.demo.dto.newsdto.AllNewsWithViewsDTO;
import com.example.demo.dto.newsdto.NewsByTitleRequestDTO;
import com.example.demo.dto.newsdto.NewsByTitleResponseDTO;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.News;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private static final int TOP_FIVE_NEWS = 5;

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    public NewsByTitleResponseDTO byTitleAndCategory(NewsByTitleRequestDTO requestDTO) {
        News oNews = newsRepository.findFirstByTitleIsContaining(requestDTO.getTitle());
        long counter = oNews.getViews();
        oNews.setViews(counter + 1);
        oNews = newsRepository.save(oNews);

        return new NewsByTitleResponseDTO(oNews);
    }

    public List<AllNewsWithViewsDTO> viewsDTO() {
        List<News> allNews = newsRepository.findByOrderByViewsDesc();
        if (allNews == null || allNews.isEmpty()) {
            throw new NotFoundException("News not found");
        }
        List<AllNewsWithViewsDTO> sortedNews = new ArrayList<>();
        for (News n : allNews) {
            sortedNews.add(new AllNewsWithViewsDTO(n));
            if (sortedNews.size() > TOP_FIVE_NEWS) {
                break;
            }
        }
        return sortedNews;
    }
}
