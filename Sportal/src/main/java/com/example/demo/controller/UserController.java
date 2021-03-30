package com.example.demo.controller;

import com.example.demo.dto.userdto.*;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @Autowired
    SessionManager sessionManager;

    @PutMapping("/users")
    public RegisterUserResponseDTO register(@RequestBody RegisterUserRequestDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @PostMapping("/users")
    public EditUserResponseDTO editUser(@RequestBody EditUserProfileDTO profileDTO, HttpSession ses) {
        User u = sessionManager.getLoggedUser(ses);
        return userService.editProfile(profileDTO, u);
    }

    @PostMapping("/users/login")
    public UserWithoutPassDTO login(@RequestBody LoginUserDTO userDTO, HttpSession ses) {
        UserWithoutPassDTO responseDTO = userService.login(userDTO);
        sessionManager.loginUser(ses, responseDTO.getId());
        return responseDTO;
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession ses) {
        sessionManager.logoutUser(ses);
    }

    @GetMapping(value = "/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable long id, HttpSession ses) {
        sessionManager.getLoggedUser(ses);
        return userService.getById(id);
    }

    @PostMapping("/admin")
    public UserWithoutPassDTO logAsAdmin(@RequestBody LoginUserDTO userDTO, HttpSession ses) {
        UserWithoutPassDTO responseDTO = userService.loginAsAdmin(userDTO);
        sessionManager.loginUser(ses, responseDTO.getId());
        return responseDTO;
    }

}
