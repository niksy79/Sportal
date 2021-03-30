package com.example.demo.dto.userdto;

import com.example.demo.model.News;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPassDTO {

    private long id;
    private String username;
    private String email;
    private boolean isAdmin;


    public UserWithoutPassDTO(User user){
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        isAdmin = user.getIsAdmin();

        //TODO да направя отделно dto за визуализация на новините
    }
}
