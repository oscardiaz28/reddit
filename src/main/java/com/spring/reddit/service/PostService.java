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

    public PostResponse save(PostDto postDto){
        Subreddit subreddit = subredditRepository.findByName(postDto.getSubredditName())
                        .orElseThrow( () -> new RedditException("Subreddit not found "));
        User user = authService.getCurrentUser();
        Post post = PostMapper.dtoToPost( postDto, subreddit, user );

        Post postSaved = postRepository.save(post);

        return PostMapper.postToDto(postSaved);
        // TODO: COMPLETAR METODO PARA GUADAR POST, Y CREAR OBJETO POST RESPONSE PARA EVITAR EL BUCLE
    }

    public List<PostResponse> getAll(){
        List<PostResponse> posts = postRepository.findAll().stream()
                .map( post -> PostMapper.postToDto(post) )
                .collect(Collectors.toList());
        return posts;
    }

}
