package com.spring.reddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.spring.reddit.dto.PostDto;
import com.spring.reddit.dto.PostResponse;
import com.spring.reddit.model.Comment;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.Subreddit;
import com.spring.reddit.model.User;
import com.spring.reddit.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@AllArgsConstructor
public class PostMapper {

    private final CommentRepository commentRepository;

    public Post dtoToPost(PostDto dto, Subreddit subreddit, User user){
        String rawName = dto.getPostName();
        String url = rawName.trim().replace(" ", "_");
        Post post = new Post();
        post.setPostName(rawName);
        post.setUrl(url);
        post.setDescription(dto.getDescription());
        post.setUser(user);
        post.setSubreddit(subreddit);
        post.setCreatedDate(Instant.now());
        post.setVoteCount(0);
        return post;
    }
    public PostResponse postToDto(Post post){
        List<Comment> comments = commentRepository.findCommentsByPostId(post.getPostId());
        return PostResponse.builder()
                .id(post.getPostId())
                .name(post.getPostName())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .subreddit(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .commentCount(comments.size())
                .duration( getDuration(post) )
                .build();
    }

    public String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
