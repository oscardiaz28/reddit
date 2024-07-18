package com.spring.reddit.controllers;

import com.spring.reddit.dto.CommentsDto;
import com.spring.reddit.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto){
        commentsService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long id){
        List<CommentsDto> comments = commentsService.getCommentsByPost(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
