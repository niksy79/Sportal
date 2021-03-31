package com.example.demo.service;
import com.example.demo.dto.newsdto.*;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private static final int TOP_FIVE_NEWS = 5;

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<NewsByTitleResponseDTO> findByName(NewsByTitleRequestDTO requestDTO) {
        List<News> filteredNews = newsRepository.findByTitleContaining(requestDTO.getTitle());
        List<NewsByTitleResponseDTO> searchedNews = new ArrayList<>();
        for (News n : filteredNews) {
            searchedNews.add(new NewsByTitleResponseDTO(n));
        }

        return searchedNews;
    }

    public List<AllNewsWithViewsDTO> viewsDTO() {
        List<News> allNews = newsRepository.findByOrderByViewsDesc()
                .stream()
                .limit(TOP_FIVE_NEWS)
                .collect(Collectors.toList());
        List<AllNewsWithViewsDTO> sortedNews = new ArrayList<>();
        for (News n : allNews) {
            sortedNews.add(new AllNewsWithViewsDTO(n));
        }
        return sortedNews;
    }

    public List<NewsByCategoryAndTitleDTO> filter(NewsSearchRequestDTO requestDTO) {
        List<News> allNews = newsRepository.findAll();
        if (allNews.isEmpty()) {
            throw new NotFoundException("News or category not found");
        }
        List<NewsByCategoryAndTitleDTO> filteredNews = new ArrayList<>();
        for (News n : allNews) {
            if (requestDTO.getCategoryName().equals(n.getCategory().getName())) {
                if (n.getTitle().contains(requestDTO.getTitle())) {
                    filteredNews.add(new NewsByCategoryAndTitleDTO(n));
                }
            }
        }
        return filteredNews;
    }

    public AddNewsResponseDTO addNews(AddNewsRequestDTO requestDTO, User user) {
        News news = new News(requestDTO);
        Category c = categoryRepository.findByName(requestDTO.getCategoryName());
        if (requestDTO.getCategoryName().isEmpty() || requestDTO.getContent().length() < 20
                || requestDTO.getTitle().length() < 5) {
            throw new BadRequestException("please fill in all fields correctly");
        }
        if (c == null) {
            c = new Category(requestDTO.getCategoryName());
            categoryRepository.save(c);
            news.setCategory(c);
        }
        if (c.getName().equals(requestDTO.getCategoryName())) {
            news.setCategory(c);
        }
        news.setOwner(user);
        news.setCreatedAt(LocalDateTime.now());
        news = newsRepository.save(news);

        return new AddNewsResponseDTO(news);
    }

    public NewsRepository getNewsRepository() {
        return newsRepository;
    }

    public ReadNewsDTO readRandomNews() {
        List<News> news = newsRepository.findAll();
        Collections.shuffle(news);
        if (news.isEmpty()) {
            throw new NotFoundException("Not found ahy news");
        }
        News n = news.get(0);
        ReadNewsDTO getNews = new ReadNewsDTO(n);
        long counter = n.getViews();
        n.setViews(counter + 1);
        newsRepository.save(n);

        return getNews;


    }

}
