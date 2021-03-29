package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddNewsRequestDTO {

    private long id;
    private int categoryId;
    private String title;
    private String content;



}
