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
import com.example.demo.model.repository.NewsRepository;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserRepository userRepository;


    public CommentLikeResponseDTO likeUnlikeComment(CommentLikeRequestDTO requestDTO, User user){
        Optional<Comment> likedComment = commentRepository.findById(requestDTO.getId());
        if (likedComment.isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        if (!user.getLikedComments().contains(likedComment.get())){
            user.getLikedComments().add(likedComment.get());
        }
        else {
            user.getLikedComments().remove(likedComment.get());
        }
        userRepository.save(user);

        return new CommentLikeResponseDTO(likedComment.get());
    }

    public CommentLikeResponseDTO dislikeComment(CommentLikeRequestDTO requestDTO, User user){
        Optional<Comment> disComment = commentRepository.findById(requestDTO.getId());

        if (disComment.isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        if (!user.getLikedComments().contains(disComment.get())){
            user.getDislikedComments().add(disComment.get());
        }
        else {
            if (user.getDislikedComments().contains(disComment.get())){
                throw new BadRequestException("you already dislike this comment");
            }
            user.getLikedComments().remove(disComment.get());
            user.getDislikedComments().add(disComment.get());
        }
        userRepository.save(user);

        return new CommentLikeResponseDTO(disComment.get());
    }


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
