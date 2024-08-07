package com.spring.reddit.repository;

import com.spring.reddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :id")
    List<Comment> findCommentsByPostId(Long id);
}
