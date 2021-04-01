package com.example.demo.dto.newsdto;

import com.example.demo.model.News;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewsByTitleResponseDTO {


    private String title;
    private String content;
    private long views;


    public NewsByTitleResponseDTO(News n) {
        title = n.getTitle();
        content = n.getContent();
        views = n.getViews();

    }
}
