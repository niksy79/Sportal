package com.example.demo.dto.newsdto;

import com.example.demo.model.Category;
import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewsByCategoryDTO {


    private String categoryName;
    private List<News> news;

    public NewsByCategoryDTO(Category c) {
        categoryName = c.getName();
        news = c.getNews();
    }

}
