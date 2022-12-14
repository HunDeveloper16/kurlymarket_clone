package com.example.kurlymarket_clone.repository;



import com.example.kurlymarket_clone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Post findPostById(Long id);

}
