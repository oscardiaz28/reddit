package com.spring.reddit.repository;

import com.spring.reddit.model.Post;
import com.spring.reddit.model.User;
import com.spring.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    /**
     SELECT * FROM vote where post_id = ? and user_id = ? order by id DESC LIMIT 1;
     */
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User user);
}
