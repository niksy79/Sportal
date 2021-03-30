package com.example.demo.dto.newsdto;

import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllNewsWithViewsDTO {

    private String title;
    private String content;
    private long views;

    public AllNewsWithViewsDTO(News n) {
        title = n.getTitle();
        content = n.getContent();
        views = n.getViews();
    }
}
