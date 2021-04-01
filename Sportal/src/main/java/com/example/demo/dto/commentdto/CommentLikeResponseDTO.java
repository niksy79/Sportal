package com.example.demo.dto.commentdto;

import com.example.demo.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentLikeResponseDTO {

    private String newsTitle;
    private String newsContent;
    private String comment;
    private int likes;
    private int dislikes;

    public CommentLikeResponseDTO(Comment c){
        newsTitle = c.getCommentedNews().getTitle();
        newsContent = c.getCommentedNews().getContent();
        comment = c.getContent();
        likes = c.getLikers().size();
        dislikes = c.getDislikers().size();

    }
}
