package com.example.demo.model;
import com.example.demo.dto.newsdto.AddNewsRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @JsonBackReference
    private User owner;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "news")
    @JsonManagedReference
    List<Image> newsImages;

    @JsonManagedReference
    @OneToMany(mappedBy = "commentedNews")
    List<Comment> newsComments;




    public News(AddNewsRequestDTO requestDTO){
        title = requestDTO.getTitle();
        content = requestDTO.getContent();

    }
}
