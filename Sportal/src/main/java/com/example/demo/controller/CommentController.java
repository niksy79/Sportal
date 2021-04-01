package com.example.demo.controller;
import com.example.demo.dto.commentdto.CommentAddRequestDTO;
import com.example.demo.dto.commentdto.CommentAddResponseDTO;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (!user.getIsAdmin()){
            throw new AuthenticationException("You have to log in");
        }
        return commentService.writeComment(requestDTO, user);
    }
}
