package com.example.demo.dto.commentdto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class CommentAddRequestDTO {


    @NotBlank
    @Size(min = 2,max = 500, message = "Comment must have at least 2 characters and max 500")
    private String text;
    private long newsId;

}
