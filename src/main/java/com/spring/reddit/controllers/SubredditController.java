package com.spring.reddit.controllers;

import com.spring.reddit.dto.SubredditDto;
import com.spring.reddit.model.Subreddit;
import com.spring.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;
    @PostMapping
    public ResponseEntity<?> createSubreddit(@RequestBody SubredditDto subredditDto){
        return new ResponseEntity<>( subredditService.save(subredditDto), HttpStatus.CREATED );
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddit(){
        return new ResponseEntity<>(subredditService.getAll(), HttpStatus.OK);
    }


}
