package com.example.demo.controller;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.model.User;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;

@Component
public class SessionManager {
    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser(HttpSession session) {
        if (session.getAttribute(LOGGED_USER_ID) == null) {
            throw new AuthenticationException("You have to log in");
        } else {
            long userId = (long) session.getAttribute(LOGGED_USER_ID);
            return userRepository.findById(userId).get();
        }
    }

    public void loginUser(HttpSession ses, long id) {
        ses.setAttribute(LOGGED_USER_ID, id);
    }

    public void logoutUser(HttpSession ses) {
        ses.invalidate();
    }
}
