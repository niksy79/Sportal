package com.example.demo.dto.newsdto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddNewsRequestDTO {

    private long id;
    @NotBlank
    @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters")
    private String title;
    @NotBlank
    @Size(min = 5, max = 5000, message = "content must be between 5 and 5000 characters")
    private String content;
    @NotEmpty
    private String categoryName;

}
