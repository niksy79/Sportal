package com.example.demo.controller;
import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.commentdto.CommentLikeRequestDTO;
import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class CommentController extends AbstractController{

    @Autowired
    CommentService commentService;
    @Autowired
    SessionManager sessionManager;


    @PutMapping("/comments")
    public CommentAddResponseDTO add(@RequestBody CommentAddRequestDTO requestDTO, HttpSession ses){
        User user = sessionManager.getLoggedUser(ses);

        return commentService.writeComment(requestDTO, user);
    }

    @PostMapping("/comments/like")
    public CommentLikeResponseDTO likeDislike(@RequestBody CommentLikeRequestDTO requestDTO, HttpSession ses){
        User user = sessionManager.getLoggedUser(ses);

        return commentService.likeUnlikeComment(requestDTO, user);
    }

    @PostMapping("/comments/dislike")
    public CommentLikeResponseDTO dislike(@RequestBody CommentLikeRequestDTO requestDTO, HttpSession ses){
        User user = sessionManager.getLoggedUser(ses);

        return commentService.dislikeComment(requestDTO, user);
    }
}
