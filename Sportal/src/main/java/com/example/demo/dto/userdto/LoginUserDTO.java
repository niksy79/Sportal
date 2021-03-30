package com.example.demo.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginUserDTO {


    private String username;
    private String password;
    private boolean isAdmin;


}
