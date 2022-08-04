package com.example.kurlymarket_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String salesUnit;
    private String weight;
    private int price;
    private MultipartFile image;
}
