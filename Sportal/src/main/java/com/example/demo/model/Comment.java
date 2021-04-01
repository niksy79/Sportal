package com.example.demo.model;

import com.example.demo.dto.commentdto.CommentLikeResponseDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private LocalDateTime createdAt;
    private int likes;
    private int dislikes;

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

    @ManyToMany(mappedBy = "dislikedComments")
    @JsonBackReference
    private List<User> dislikers;



    public Comment(String content, LocalDateTime createdAt, News commentedNews, User commentingUser) {
        this.content = content;
        this.createdAt = createdAt;
        this.commentedNews = commentedNews;
        this.commentingUser = commentingUser;
        this.likes = likers.size();
        this.dislikes = dislikers.size();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
