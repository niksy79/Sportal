package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditUserProfileDTO {

    private long id;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String email;
}
