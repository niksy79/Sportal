package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    private LocalDate createdAt;
    private LocalDateTime lastLogin;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<News> news;

    @OneToMany(mappedBy = "commentingUser")
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "users_like_comments",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")}
    )
    @JsonManagedReference
    private List<Comment> likedComments;

    @ManyToMany
    @JoinTable(
            name = "users_dislike_comments",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")}
    )
    @JsonManagedReference
    private List<Comment> dislikedComments;

    public User(String username, String password, String email, LocalDate createdAt, LocalDateTime lastLogin, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
