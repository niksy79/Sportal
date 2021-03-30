package com.example.demo.dto.userdto;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserResponseDTO {

    private long id;
    private String username;
    private String email;

    public RegisterUserResponseDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
