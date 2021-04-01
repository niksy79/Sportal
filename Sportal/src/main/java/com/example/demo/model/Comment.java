package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @JsonBackReference
    News commentedNews;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    User commentingUser;

    @ManyToMany(mappedBy = "likedComments")
    @JsonBackReference
    private List<User> likers;

    public Comment(String content, LocalDateTime createdAt, News commentedNews, User commentingUser) {
        this.content = content;
        this.createdAt = createdAt;
        this.commentedNews = commentedNews;
        this.commentingUser = commentingUser;
    }
}
