package com.example.demo.model;

import com.example.demo.dto.userdto.EditUserProfileDTO;
import com.example.demo.dto.userdto.RegisterUserRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Component
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private LocalDate createdAt;
    @Column
    private LocalDateTime lastLogin;
    @Column
    private Boolean isAdmin;


    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<News> news;


    @OneToMany(mappedBy = "commentingUser")
    @JsonManagedReference
    private List<Comment> comments;

    public User(RegisterUserRequestDTO userRequestDTO){
        id = userRequestDTO.getId();
        username = userRequestDTO.getUsername();
        password = userRequestDTO.getPassword();
        email = userRequestDTO.getEmail();
    }


    public User(EditUserProfileDTO profileDTO) {
        id = profileDTO.getId();
        username = profileDTO.getUsername();
        password = profileDTO.getNewPassword();
        email = profileDTO.getEmail();
    }
}
