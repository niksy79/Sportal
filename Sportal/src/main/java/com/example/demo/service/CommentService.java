package com.example.demo.service;
import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.commentdto.CommentLikeRequestDTO;
import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.model.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;


    @Transactional
    public CommentLikeResponseDTO likeUnlikeComment(CommentLikeRequestDTO requestDTO, User user) {
        Comment likedComment = getCommentById(requestDTO.getId());
        if (!user.getLikedComments().contains(likedComment)) {
            user.getLikedComments().add(likedComment);
            user.getDislikedComments().remove(likedComment);
        } else {
            user.getLikedComments().remove(likedComment);
        }
        userService.save(user);
        commentRepository.save(likedComment);

        return new CommentLikeResponseDTO(likedComment);
    }

    public CommentLikeResponseDTO dislikeComment(CommentLikeRequestDTO requestDTO, User user) {
        Comment disComment = getCommentById(requestDTO.getId());
        if (user.getDislikedComments().contains(disComment)){
            throw new BadRequestException("you already dislike this comment");
        }
        else {
            user.getDislikedComments().add(disComment);
            user.getLikedComments().remove(disComment);
        }
        userService.save(user);

        return new CommentLikeResponseDTO(disComment);
    }


    public CommentAddResponseDTO writeComment(CommentAddRequestDTO requestDTO, User loggedUser) {
        News news = newsService.getByID(requestDTO.getNewsId());
        Comment comment = new Comment(requestDTO.getText(), LocalDateTime.now(), news, loggedUser);
        if (comment.getContent().isEmpty()) {
            throw new BadRequestException("Comment must have at least one character");
        }
        CommentAddResponseDTO responseDTO = new CommentAddResponseDTO(news);
        responseDTO.setCommentText(requestDTO.getText());
        commentRepository.save(comment);

        return responseDTO;
    }

    public void deleteComment(long id){
        Comment comment = getCommentById(id);
        commentRepository.delete(comment);
    }

    public Comment getCommentById(long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        return comment.get();
    }
}
