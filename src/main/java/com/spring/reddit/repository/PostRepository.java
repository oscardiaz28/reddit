package com.spring.reddit.repository;

import com.spring.reddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p from Post p WHERE p.postId = :id")
    Optional<Post> findById(@Param("id") Long id);

    Optional<Post> findByUrl(String url);
}
