package com.example.demo.dto.newsdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsByNameAndCategoryRequestDTO {

    private String title;
    private String categoryName;
}
