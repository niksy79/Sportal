package com.example.demo.dto.newsdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsSearchRequestDTO {

    private String categoryName;
    private String title;
}
