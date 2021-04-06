package com.example.demo.controller;

import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.dto.commentdto.CommentLikeRequestDTO;
import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.SessionManager;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController extends AbstractController {

    @Autowired
    CommentService commentService;
    @Autowired
    SessionManager sessionManager;
    @Autowired
    UserService userService;


    @PutMapping("/comments")
    public CommentAddResponseDTO add(@Valid @RequestBody CommentAddRequestDTO requestDTO, HttpSession ses) {
        User user = sessionManager.getLoggedUser(ses);
        return commentService.writeComment(requestDTO, user);
    }

    @PostMapping("/comments/like")
    public CommentLikeResponseDTO likeDislike(@RequestBody CommentLikeRequestDTO requestDTO, HttpSession ses) {
        User user = sessionManager.getLoggedUser(ses);
        return commentService.likeUnlikeComment(requestDTO, user);
    }

    @PostMapping("/comments/{id}")
    @Transactional
    public String delete(@PathVariable long id, HttpSession ses) {
        User user = sessionManager.getLoggedUser(ses);
        commentService.deleteComment(id);
        userService.save(user);
        return "comment deleted successfully";
    }

    @GetMapping("/comments/top")
    public List<CommentLikeResponseDTO> topTenLikedComments() {
        return commentService.mostLikedComments();
    }

    @PostMapping("/comments/dislike")
    public CommentLikeResponseDTO dislike(@RequestBody CommentLikeRequestDTO requestDTO, HttpSession ses) {
        User user = sessionManager.getLoggedUser(ses);
        return commentService.dislikeComment(requestDTO, user);
    }
}
