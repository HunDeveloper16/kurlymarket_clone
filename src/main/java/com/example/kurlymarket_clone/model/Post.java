package com.example.kurlymarket_clone.model;


import com.example.kurlymarket_clone.dto.PostRequestDto;
import com.example.kurlymarket_clone.model.Embedded.File;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID",nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String salesUnit;
    @Column(nullable = false)
    private String weight;
    @Column(nullable = false)
    private int price;
    @Embedded
    private File file;


    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.salesUnit = postRequestDto.getSalesUnit();
        this.weight = postRequestDto.getWeight();
        this.price = postRequestDto.getPrice();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.salesUnit = postRequestDto.getSalesUnit();
        this.weight = postRequestDto.getWeight();
        this.price = postRequestDto.getPrice();
    }

}






