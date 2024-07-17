package com.spring.reddit.mapper;

import com.spring.reddit.dto.PostDto;
import com.spring.reddit.dto.PostResponse;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.Subreddit;
import com.spring.reddit.model.User;

import java.time.Instant;

public class PostMapper {
    public static Post dtoToPost(PostDto dto, Subreddit subreddit, User user){
        Post post = new Post();
        post.setPostName(dto.getPostName());
        post.setDescription(dto.getDescription());
        post.setUser(user);
        post.setSubreddit(subreddit);
        post.setCreatedDate(Instant.now());
        post.setVoteCount(0);
        return post;
    }
    public static PostResponse postToDto(Post post){
        return PostResponse.builder()
                .id(post.getPostId())
                .name(post.getPostName())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .subreddit(post.getSubreddit().getName())
                .build();
    }
}
