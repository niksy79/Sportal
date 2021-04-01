package com.example.demo.dto.userdto;

import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.newsdto.NewsByTitleResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {

    private String username;
    private List<News> news;
    private long likes;


    public UserProfileDTO(User user){
        username = user.getUsername();
        news = user.getNews();



    }



}
