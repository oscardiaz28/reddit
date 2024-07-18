package com.spring.reddit.service;

import com.spring.reddit.dto.PostDto;
import com.spring.reddit.dto.PostResponse;
import com.spring.reddit.exceptions.RedditException;
import com.spring.reddit.mapper.PostMapper;
import com.spring.reddit.model.Post;
import com.spring.reddit.model.Subreddit;
import com.spring.reddit.model.User;
import com.spring.reddit.repository.PostRepository;
import com.spring.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public PostResponse save(PostDto postDto){
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
                        .orElseThrow( () -> new RedditException("Subreddit not found "));
        User user = authService.getCurrentUser();
        Post post = postMapper.dtoToPost( postDto, subreddit, user );
        Post postSaved = postRepository.save(post);
        return postMapper.postToDto(postSaved);
    }

    public PostResponse getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow( () -> new RedditException("Post not found") );
        return postMapper.postToDto(post);
    }

    public PostResponse getPostByUrl(String url){
        Post post = postRepository.findByUrl(url).orElseThrow( () -> new RedditException("Post not found") );
        return postMapper.postToDto(post);
    }

    public List<PostResponse> getAll(){
        List<PostResponse> posts = postRepository.findAll().stream()
                .map( post -> postMapper.postToDto(post) )
                .collect(Collectors.toList());
        return posts;
    }

}
