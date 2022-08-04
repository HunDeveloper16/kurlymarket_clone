package com.example.kurlymarket_clone.controller;


import com.example.kurlymarket_clone.dto.PostRequestDto;
import com.example.kurlymarket_clone.dto.PostResponseDto;
import com.example.kurlymarket_clone.model.Post;
import com.example.kurlymarket_clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;
    //(메인페이지)게시글 작성
    @PostMapping("/api/post")
    public ResponseEntity<Post> post(@ModelAttribute PostRequestDto postRequestDto) throws IOException {
        postService.savepost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }
//(메인페이지)게시글 삭제
    @DeleteMapping("/api/post/{postId}")
        public ResponseEntity<Long> deletepost(@PathVariable Long postId) {
        postService.deleteposting(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

//상세 페이지 조회
@GetMapping("api/post/{postId}")
public ResponseEntity<PostResponseDto> mainpost(@PathVariable Long postId){
    PostResponseDto postResponseDto = postService.detailposting(postId);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(postResponseDto);
}

////(메인페이지)조회
@GetMapping("/api/main")
public ResponseEntity<List<PostResponseDto>> mainpost(){
    List<PostResponseDto> postResponseDto= postService.mainposting();
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(postResponseDto);
}


//(메인페이지)게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<Post> updatepost(@PathVariable Long postId,@ModelAttribute PostRequestDto postRequestDto){
        postService.updatepost(postId,postRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

}
