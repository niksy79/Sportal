package com.example.demo.dto.newsdto;

import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewsByCategoryAndTitleDTO {

    private String title;
    private String content;
    private long views;
    private String categoryName;

    public NewsByCategoryAndTitleDTO(News news){
        title = news.getTitle();
        content = news.getContent();
        views = news.getViews();
        categoryName = news.getCategory().getName();
    }



}
