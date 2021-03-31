package com.example.demo.dto.newsdto;


import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddNewsResponseDTO {

    private long id;
    private String title;
    private String content;
    private long views = 0;



    public  AddNewsResponseDTO(News news){
        id = news.getId();
        title = news.getTitle();
        content = news.getContent();
    }
}
