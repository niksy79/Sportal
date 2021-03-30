package com.example.demo.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserRequestDTO {

    private long id;
    private String username;
    private String password;
    private String confirm;
    private String email;

}
