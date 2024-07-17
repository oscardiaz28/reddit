package com.spring.reddit.mapper;

import com.spring.reddit.dto.SubredditDto;
import com.spring.reddit.model.Subreddit;

import java.time.Instant;

public class SubredditMapper {

    public static SubredditDto subredditToDto(Subreddit subreddit){
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    public static Subreddit dtoToSubreddit(SubredditDto dto){
        return Subreddit.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdDate(Instant.now())
                .build();
    }

}
