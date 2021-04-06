package com.example.demo.dto;
import lombok.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Component
@AllArgsConstructor
public class ErrorDTO {

    private Date timestamp;
    private String msg;
    private String details;

}
