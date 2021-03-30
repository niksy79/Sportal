package com.example.demo.dto.userdto;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditUserResponseDTO {

    private long id;
    private String username;
    private String email;

    public EditUserResponseDTO(User user){
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
