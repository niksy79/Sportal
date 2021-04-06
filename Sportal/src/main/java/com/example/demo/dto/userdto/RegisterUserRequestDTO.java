package com.example.demo.dto.userdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserRequestDTO {

    private long id;
    @NotEmpty
    @Size(min = 4, message = "username must be at least 4 characters")
    private String username;
    @NotEmpty
    @Size(min = 4, message = "username must be at least 4 characters")
    private String password;
    private String confirm;
    @NotEmpty
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")
    private String email;


}
