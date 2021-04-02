package com.example.demo.service;

import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.commentdto.CommentLikeRequestDTO;
import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.User;

import java.util.List;

public interface ICommentService {

    CommentLikeResponseDTO likeUnlikeComment(CommentLikeRequestDTO requestDTO, User user);

    CommentLikeResponseDTO dislikeComment(CommentLikeRequestDTO requestDTO, User user);

    CommentAddResponseDTO writeComment(CommentAddRequestDTO requestDTO, User loggedUser);

    List<CommentLikeResponseDTO> mostLikedComments();

    void deleteComment(long id);

    Comment getCommentById(long id);



}
