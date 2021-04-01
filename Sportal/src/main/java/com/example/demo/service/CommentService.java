package com.example.demo.service;
import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CommentRepository;
import com.example.demo.model.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    NewsRepository newsRepository;


    public CommentAddResponseDTO writeComment(CommentAddRequestDTO requestDTO, User loggedUser) {

        if (newsRepository.findById(requestDTO.getNewsId()).isEmpty()){
            throw new NotFoundException("Article not found");
        }
        News news = newsRepository.findById(requestDTO.getNewsId()).get();
        Comment comment = new Comment(requestDTO.getText(), LocalDateTime.now(), news, loggedUser);
        if (comment.getContent().isEmpty()){
            throw new BadRequestException("Comment must have at least one character");
        }
        CommentAddResponseDTO responseDTO = new CommentAddResponseDTO(news);
        responseDTO.setCommentText(requestDTO.getText());
        commentRepository.save(comment);

        return responseDTO;
    }
}
