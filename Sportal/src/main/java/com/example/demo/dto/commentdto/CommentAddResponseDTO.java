package com.example.demo.dto.commentdto;

import com.example.demo.model.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentAddResponseDTO {

    private String newsTitle;
    private String newsContent;
    private String commentText;

    public CommentAddResponseDTO(News n) {
        newsTitle = n.getTitle();
        newsContent = n.getContent();
    }

}
