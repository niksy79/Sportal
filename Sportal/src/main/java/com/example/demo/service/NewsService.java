package com.example.demo.service;
import com.example.demo.dto.newsdto.*;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService implements INewsService{

    private static final int TOP_FIVE_NEWS = 5;
    @Autowired
    CategoryService categoryService;
    @Autowired
    NewsRepository newsRepository;


    @Override
    public List<NewsByTitleResponseDTO> findByName(NewsByTitleRequestDTO requestDTO) {
        List<News> filteredNews = newsRepository.findByTitleContaining(requestDTO.getTitle());
        List<NewsByTitleResponseDTO> searchedNews = new ArrayList<>();
        for (News n : filteredNews) {
            searchedNews.add(new NewsByTitleResponseDTO(n));
        }

        return searchedNews;
    }

    @Override
    public List<AllNewsWithViewsDTO> topFiveNews() {
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


    @Transactional
    @Override
    public AddNewsResponseDTO editNews(AddNewsRequestDTO requestDTO) {
        News editedNews = getByID(requestDTO.getId());;
        Category category = categoryService.findByName(requestDTO.getCategoryName());
        if(checkIsValidString(requestDTO) && category.getName().equals(requestDTO.getCategoryName())){
            editedNews.setTitle(requestDTO.getTitle());
            editedNews.setContent(requestDTO.getContent());
            editedNews.setCategory(category);
            categoryService.save(category);
            newsRepository.save(editedNews);
        }
        return new AddNewsResponseDTO(editedNews);
    }

    @Transactional
    public AddNewsResponseDTO addNews(AddNewsRequestDTO requestDTO, User user) {
        News news = new News(requestDTO);
        Category c = categoryService.findByName(requestDTO.getCategoryName());

        if (c == null) {
            c = new Category(requestDTO.getCategoryName());
            categoryService.save(c);
            news.setCategory(c);
        }
       if (checkIsValidString(requestDTO) && c.getName().equals(requestDTO.getCategoryName())){
           news.setCategory(c);
           news.setOwner(user);
           news.setCreatedAt(LocalDateTime.now());
           news = newsRepository.save(news);
       }

       return new AddNewsResponseDTO(news);
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

    public News getByID(long id){
        Optional<News> news = newsRepository.findById(id);
        if (news.isEmpty()){
            throw new NotFoundException("news not found");
        }
        return news.get();
    }

    @Override
    public List<ReadNewsDTO>latestNews() {
        List<News> newsByCreateTime = newsRepository.findByOrderByCreatedAtDesc()
                .stream()
                .limit(50)
                .collect(Collectors.toList());
        List<ReadNewsDTO> latestNews = new ArrayList<>();
        for (News n : newsByCreateTime){
            latestNews.add(new ReadNewsDTO(n));
        }
        return latestNews;
    }

    public boolean checkIsValidString(AddNewsRequestDTO requestDTO){
        if (requestDTO.getCategoryName().isEmpty() || requestDTO.getContent().length() < 20
                || requestDTO.getTitle().length() < 5) {
            throw new BadRequestException("please fill in all fields correctly");
        }
        return true;
    }

    public void deleteNews(long id){
        News news = getByID(id);
        newsRepository.delete(news);
    }
}
