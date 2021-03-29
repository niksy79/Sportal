package com.example.demo.model;

import com.example.demo.dto.AddNewsRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Component
@Getter
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long views;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User owner;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public News(AddNewsRequestDTO requestDTO){
        id = requestDTO.getId();
        title = requestDTO.getTitle();
        content = requestDTO.getContent();
        views = 0;
    }
}
