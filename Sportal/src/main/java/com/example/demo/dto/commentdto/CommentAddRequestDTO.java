package com.example.demo.dto.commentdto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentAddRequestDTO {

    private String text;
    private long newsId;

}
