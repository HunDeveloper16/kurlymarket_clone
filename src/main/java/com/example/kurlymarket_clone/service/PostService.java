package com.example.kurlymarket_clone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.kurlymarket_clone.dto.FileDto;
import com.example.kurlymarket_clone.dto.PostRequestDto;
import com.example.kurlymarket_clone.dto.PostResponseDto;
import com.example.kurlymarket_clone.model.Embedded.File;
import com.example.kurlymarket_clone.model.Post;
import com.example.kurlymarket_clone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Setter
@RequiredArgsConstructor
public class PostService {

    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;

    private String S3Bucket = "test-bucket-hong";


//게시글 생성
    @Transactional
    public void savepost(PostRequestDto postresquestdto) throws IOException {

        FileDto fileDto = createFile(postresquestdto.getImage());
        File saveFile = new File(fileDto);
        Post post = Post.builder()
                .title(postresquestdto.getTitle())
                .salesUnit(postresquestdto.getSalesUnit())
                .weight(postresquestdto.getWeight())
                .price(postresquestdto.getPrice())
                .file(saveFile)
                .build();

        postRepository.save(post);
    }
//이미지생성
    public FileDto createFile(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        long size = file.getSize();
        String originalFileExtension = StringUtils.getFilenameExtension(originalName);

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);

        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, originalName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        String fileName =
                UUID.randomUUID()
                        .toString()
                        .replaceAll("-", "") + "." + originalFileExtension;

        String fileUrl = amazonS3Client.getUrl(S3Bucket, originalName).toString();
        return new FileDto(fileUrl, originalName, fileName);
    }

//게시글 삭제
    @Transactional
    public void deleteposting(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글을 찾을 수 없습니다.")
        );
        postRepository.delete(post);
    }

//게시글 수정
@Transactional
    public void updatepost(Long postId, PostRequestDto postResquestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글을 찾을 수 없습니다.")
        );
        post.update(postResquestDto);
    }

    //상세페이지 조회
    public PostResponseDto detailposting(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글을 찾을 수 없습니다."));
       return new PostResponseDto(post);
    }


//메인페이지 조회
    public List<PostResponseDto> mainposting() {
        List<Post> posts =postRepository.findAll();
        List<PostResponseDto> responseDtos= new ArrayList<>();
        for(Post post: posts){
            PostResponseDto postResponseDto =new PostResponseDto(post);
            responseDtos.add(postResponseDto);
        }
        return responseDtos;
    }

//insert into POST (ID, IMAGE_URL, PRICE, SALES_UNIT ,TITLE , WEIGHT) values (1, 'Suzuki', 1, '123' , '1234' , '1234' );
}






