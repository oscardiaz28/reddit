package com.spring.reddit.controllers;

import com.spring.reddit.dto.VoteDto;
import com.spring.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public void vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
    }

}
