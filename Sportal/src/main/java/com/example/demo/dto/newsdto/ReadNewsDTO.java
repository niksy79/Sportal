package com.example.demo.dto.newsdto;

import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadNewsDTO {

    public String title;
    public String content;
    public long views;

    public ReadNewsDTO(News news){
        title = news.getTitle();
        content = news.getContent();
        views = news.getViews();
    }
}
