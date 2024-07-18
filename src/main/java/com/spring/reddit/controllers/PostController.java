package com.spring.reddit.controllers;

import com.spring.reddit.dto.PostDto;
import com.spring.reddit.dto.PostResponse;
import com.spring.reddit.model.Post;
import com.spring.reddit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        postService.save(postDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        PostResponse response = postService.getPostById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/url/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable String url){
        PostResponse response = postService.getPostByUrl(url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public List<PostResponse> getAll(){
        return postService.getAll();
    }



}