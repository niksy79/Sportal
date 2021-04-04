package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.model.repository.CategoryRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CategoryController extends AbstractController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SessionManager sessionManager;
    @Autowired
    UserService userService;

    @PutMapping("/category")
    public String addCategory(@Valid @RequestBody Category c, HttpSession ses) {
        User user = sessionManager.getLoggedUser(ses);
        userService.checkIsUserAdmin(user);
        categoryRepository.save(c);
        return "you added category: " + c.getName();
    }
}
